package eserciziDue.garaAppalti;

import java.io.*;
import java.net.*;
import java.util.*;

public class Giudice {

    private List<Offerta> offerte;
    private Richiesta richiestaEnte;

    public Giudice() {
        this.offerte = Collections.synchronizedList(new ArrayList<>());
    }

    public void leggiRichiestaEnte(){
        Richiesta richiesta = null;
        try{
            ServerSocket serverSocket = new ServerSocket(2000);
            Socket socket = serverSocket.accept();
            //leggiamo la richiesta da parte dell'ente
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            richiesta = (Richiesta) objectInputStream.readObject();
            socket.close();
        }
        catch(IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
        this.richiestaEnte = richiesta;
    }

    public void inviaRichiestaAPartecipanti(){
        MulticastSocket socket = null;
        try{
          //seleziono il gruppo dove mandare le offerte
          socket = new MulticastSocket(3000);
          InetAddress group = InetAddress.getByName("230.0.0.1");

          // buffer per la richiesta
          String richiestaDaMandare = this.richiestaEnte.toString();        //converto la richiesta in Stringa
          byte[] buffer =  richiestaDaMandare.getBytes();       //converto la richiesta in Bytes

          //invio gli estremi
          DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, 3000);
          socket.send(packet);
          socket.close();

        }
        catch(IOException e){
            System.out.println(e);
        }
    }


    public void riceviOfferte(){
        try{
            ServerSocket serverSocket = new ServerSocket(4000);
            int i = 0;
            while(i < 12){
                Socket socket = serverSocket.accept();
                GiudiceHandler thread = new GiudiceHandler(socket, this.offerte);
                thread.start();
                i++;
            }
        }
        catch (IOException e){
            System.out.println(e);
        }
    }

    public Offerta selezionaMinimo(){
        Offerta offertaMinima = this.offerte.get(0);
        int prezzo = this.offerte.get(0).getImporto();
        for (Offerta o : this.offerte){
            if(o.getImporto() < prezzo){
                offertaMinima = o;
                prezzo = o.getImporto();
            }
        }
        return offertaMinima;
    }

    public void inviaRisultati(){
        String vincitore = "Vincitore: "+selezionaMinimo().getId()+" Importo: "+selezionaMinimo().getImporto();
        MulticastSocket socket = null;
        try{
            socket = new MulticastSocket(3000);
            InetAddress group = InetAddress.getByName("230.0.0.1");

            byte[] buff = vincitore.getBytes();
            DatagramPacket packet = new DatagramPacket(buff, buff.length, group, 3000);

            socket.send(packet);
            socket.close();

        }
        catch (IOException e){
            System.out.println(e);
        }
    }


}


class GiudiceHandler extends Thread{
    private Socket socket;
    private List<Offerta> offerte;

    public GiudiceHandler(Socket socket, List<Offerta> offerte){
        this.socket = socket;
        this.offerte = offerte;
    }

    public void storeOfferta(Offerta offerta){
        offerte.add(offerta);
    }

    public void run(){
    try{
        //riceve le offerte
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Offerta offerta = (Offerta) objectInputStream.readObject();

        storeOfferta(offerta);
    }
    catch(IOException | ClassNotFoundException e){
        System.out.println(e);
    }

    }
}