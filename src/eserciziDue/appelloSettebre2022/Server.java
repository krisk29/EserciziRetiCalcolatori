package eserciziDue.appelloSettebre2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;


public class Server {

    private Map<String,HashMap<Integer,Long>> postiOperazioni = Collections.synchronizedMap(new HashMap<>());   //categoria - <id, password>
    private int portTCP = 3000;
    private int portTCP2= 5000;

    public Server(){
        postiOperazioni.put("fiscale",new HashMap<Integer,Long>());
        postiOperazioni.put("catasto",new HashMap<Integer,Long>());
        postiOperazioni.put("sociale",new HashMap<Integer,Long>());
        postiOperazioni.put("scuola",new HashMap<Integer,Long>());
    /*new Thread->{
        )};*/
    }

    public void riceviCategoria(){
        try{
            ServerSocket  serverSocket = new ServerSocket(portTCP);
            while(true){
                Socket s = serverSocket.accept();
                SalvaPrenotazioni salva = new SalvaPrenotazioni(postiOperazioni, s);
                salva.start();
                salva.join();
            }
        }catch(IOException | InterruptedException e){
            System.out.println(e);
        }
    }

    public void creaSessione(){
        try{
            MulticastSocket m = new MulticastSocket();
            GestioneSessioni g = new GestioneSessioni(m,postiOperazioni);
            g.start();
            g.join();
        }catch (IOException | InterruptedException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Server s = new Server();

        s.riceviCategoria();

    }
}   //server

class SalvaPrenotazioni extends Thread{
    private Map<String,HashMap<Integer,Long>> postiOperazioni;
    private Socket s;
    private Random rand = new Random();
    long min = 1000000000000000L; // 16 cifre
    long max = 9999999999999999L; // 16 cifre



    SalvaPrenotazioni(Map<String,HashMap<Integer,Long>> postiOperazioni, Socket s){
        this.postiOperazioni = postiOperazioni;
        this.s = s;
    }

    public void run(){
        try{
            if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 18 && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) <= 19)
            {
                BufferedReader bf = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String nomeCat = bf.readLine();
                if(postiOperazioni.get(nomeCat).size()<20){
                    System.out.println(postiOperazioni.toString());
                    System.out.println("Categoria "+nomeCat+ " ricevuta e salvata");
                    PrintWriter pw = new PrintWriter(s.getOutputStream());
                    long randomNumber = min + ((long)(rand.nextDouble() * (max - min)));
                    String inviare = "numProgressivo: "+postiOperazioni.get(nomeCat)+": password: "+randomNumber;
                    pw.println(inviare);
                    postiOperazioni.get(nomeCat).put(postiOperazioni.get(nomeCat).size()+1,randomNumber);
                    System.out.println(inviare+" INVIATA!");
                }

            }else{
                System.out.println("Sei fuori orario.");
            }
        }catch(IOException e){
            System.out.println(e);
        }
    }
}//SalvaPrenotazioni


class GestioneSessioni extends Thread{
    private MulticastSocket m;
    private Map<String,HashMap<Integer,Long>> postiOperazioni;

    GestioneSessioni(MulticastSocket m,Map<String,HashMap<Integer,Long>> postiOperazioni){
        this.m = m;
        this.postiOperazioni = postiOperazioni;
    }

    public void run(){
        try {
            if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 9 && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) <= 14) {
                for (String categoria : postiOperazioni.keySet()) {       //giriamo le categorie
                    for (Integer id : postiOperazioni.get(categoria).keySet()) {      //giriamo gli id
                        //manda il messaggio
                        String messaggio = categoria + " " + id;
                        byte[] buf = messaggio.getBytes();
                        DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                        m.send(packet);
                        //attende la risposta
                        Thread.sleep(30*1000);
                        String password = riceviRisposta();
                        if(password!= null && password == String.valueOf(postiOperazioni.get(categoria).get(id))){
                            inviaLinkConferenza();
                        }
                        Thread.sleep(30);
                    }
                }
            }
        }
        catch(IOException | InterruptedException e){
            System.out.println(e);
        }
    }

    private void inviaLinkConferenza() {
        try{
           Socket s = new Socket("localhost",4000);
           PrintWriter pw = new PrintWriter(s.getOutputStream());
           pw.write("SUCAFOLINO.com");
        }
        catch(IOException e){
            System.out.println(e);
        }
    }


    private String riceviRisposta() {
        try{
            ServerSocket ss = new ServerSocket(4000);
            Socket s = ss.accept();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            return bufferedReader.readLine();
        }
        catch(IOException e){
            System.out.println(e);
        }
        return "";
    }

}