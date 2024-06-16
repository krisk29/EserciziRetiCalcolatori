package eserciziDue.appelloAprile2023;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Server {
    private Random r = new Random();
    private int port = 30000+r.nextInt(10001);
    private int portUDP = 5000;
    private List<Asta> aste = Collections.synchronizedList(new ArrayList<Asta>());
    private List<Offerta> offerte = Collections.synchronizedList(new ArrayList<Offerta>());

    public Server(){
        Runnable s = () -> {
            if (!aste.isEmpty())
                avviaAsta(aste.get(0));        //supponiamo che allo scadere dell'asta essa veda eliminate
        };
       try{
           Thread t = new Thread(s);
           t.start();
           t.join();
       }
       catch(InterruptedException e){
           e.printStackTrace();
       }

    }

    /*
    * Quando il Server ha un prodotto da mettere all’asta, apre un server socket su una porta P,
      scelta a caso tra 30000 e 40000. Il server socket rimane aperto per 60 minuti, trascorsi i
      quali si chiude. Il Server, una volta aperta l’asta, invia un messaggio contente l’ID
      dell’asta, la porta P e il nome del prodotto (stringa di lunghezza max 200 caratteri) sul
      gruppo multicast caratterizzato dalla porta UDP 5000 e dall'indirizzo 230.0.0.1.*/
    public void avviaAsta(Asta asta) {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port);
            ss.setSoTimeout(60 * 60 * 1000);
            MulticastSocket m = new MulticastSocket();
            String invia = asta.getId() + " " + asta.getNome() + " " + port;
            byte[] buf = invia.getBytes();
            InetAddress group = InetAddress.getByName("230.0.0.1");
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, portUDP);
            m.send(packet);
            while (true) {
                Socket s = ss.accept();
                RiceviERispondiT rec = new RiceviERispondiT(s, offerte);
                rec.start();
                rec.join();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        } finally {
            if (ss != null) {
                try {
                    ss.close();
                    aste.remove(asta);
                    String codiceFiscaleMaxCliente = restituisciMax(asta.getId(),offerte);
                    DatagramSocket ds = new DatagramSocket();
                    byte[] buf = codiceFiscaleMaxCliente.getBytes();
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName("localhost"), 4000);
                    ds.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String restituisciMax(int id, List<Offerta> offerte) {
        int maxDenaro=0;
        String codiceMax ="";
        for(Offerta o : offerte){
            if(o.getIdAsta()==id){
                if(o.getDenaro()>maxDenaro){
                    maxDenaro=o.getDenaro();
                    codiceMax=o.getCodiceFiscale();
                }
            }
        }
        return codiceMax;
    }

    class RiceviERispondiT extends Thread{

    private Socket s;
    private List<Offerta> offerte;
    boolean isAccettato = true;

    public RiceviERispondiT(Socket s, List<Offerta> offerte){
        this.s = s;
        this.offerte = new ArrayList<>();
    }

    /*
    * riceve un oggetto Offerta contente il codice fiscale del cliente, l’ID dell’asta e l’importo di
    denaro offerto. Il Server risponde con un booleano (true/false) che indica l’accettazione o il
    rifiuto dell’offerta.
    */

    public void run(){
        try{
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            Offerta of = (Offerta) ois.readObject();
            if(of.getDenaro() < 100){
                isAccettato = false;
            }
            else{
                offerte.add(of);
            }
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            pw.write(String.valueOf(isAccettato));
        }
        catch(IOException | ClassNotFoundException e){
            System.out.println(e);
        }
        }
    }

}
