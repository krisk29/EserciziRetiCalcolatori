package eserciziDue.giugno2022;

import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private int portTCP = 3000;
    private int portTCP2 = 4000;
    private int portTCP3 = 5000;
    private Map<Offerta,Integer> scadenze; //Offerta, contaGiorni

    public Server()  {
        scadenze = Collections.synchronizedMap(new HashMap<>());
//Manca qualcosa che aggiorni giorno per giorno il contaGiorni
       try {
           Thread thread = new Thread(() -> {
               for (Offerta o : scadenze.keySet()) {
                   if (scadenze.get(o) >= 30) {
                       scadenze.remove(o);
                   }
               }
           });
           thread.start();
           thread.join();
       }catch (InterruptedException e){
           System.out.println(e);
       }

    }

    public void ricevi(){
        try{
            ServerSocket server = new ServerSocket(portTCP);
            while(true){
                Socket s = server.accept();
                RiceviT r = new RiceviT(s,scadenze);
                r.start();
                r.join();
            }
        }catch(IOException | InterruptedException e){
            System.out.println(e);
        }
    }

    public void ricevi2(){
        try{
            ServerSocket server = new ServerSocket(portTCP2);
            while(true){
                Socket s = server.accept();
                RiceviCand rc = new RiceviCand(s,scadenze);
                rc.start();
                rc.join();
            }
            }
        catch(IOException | InterruptedException e){
            System.out.println(e);
        }

    }

}

class RiceviT extends Thread{
    private Socket s;
    private Map<Offerta,Integer> scadenze;
    private AtomicInteger idNumerico= new AtomicInteger(0);
    private int portaMulticast = 6000;
    private String gruppo = "230.0.0.1";

    public RiceviT(Socket s, Map<Offerta,Integer> scadenze) {
        this.s = s;
        this.scadenze = scadenze;
    }

    public void run(){
        try{
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            Offerta o = (Offerta)ois.readObject();
            int idNum = idNumerico.getAndIncrement();
            o.setIdOfferta(idNum);
            scadenze.put(o,0);

            inviaInMulticast(o);
        }
        catch(IOException | ClassNotFoundException e){
            System.out.println(e);
        }
    }

    private void inviaInMulticast(Offerta offerta){
        try{
            MulticastSocket m = new MulticastSocket();
            String id = offerta.getIdOfferta()+" "+offerta.getRAL()+" "+offerta.getSettore()+" "+offerta.getTipo();
            byte[] buf = id.getBytes();
            InetAddress group = InetAddress.getByName(gruppo);
            DatagramPacket packet = new DatagramPacket(buf, buf.length,group,6000);
            m.send(packet);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

}

class RiceviCand extends Thread{

    private Socket s;
    private Map<Offerta,Integer> scadenze;

    public RiceviCand(Socket s, Map<Offerta,Integer> scadenze) {
        this.s = s;
        this.scadenze = scadenze;
    }
    public void run(){
        try{
            BufferedReader in = new BufferedReader( new InputStreamReader(s.getInputStream()));
            String dati = in.readLine();
            String[] parser = dati.split(" ");
            int idOffertaPersona = Integer.getInteger(parser[0]);
            if(scadenze.containsKey(idOffertaPersona)){
                Socket ss = new Socket("localhost",5000);
                PrintWriter out = new PrintWriter(ss.getOutputStream(),true);
                out.write(dati);
            }
        }
        catch (IOException e){
            System.out.println(e);
        }
    }



}