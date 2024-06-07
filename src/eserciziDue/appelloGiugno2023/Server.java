package eserciziDue.appelloGiugno2023;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private final int portaTCP = 3000;
    private final int portTCP2 = 4000;
    private Map<Sondaggio, Date> sondaggi; //sondaggio - ora iniziale
    private List<RisposteSondaggio> risposteSondaggio;

    public Server() {
        sondaggi = Collections.synchronizedMap(new HashMap<>());
        risposteSondaggio = Collections.synchronizedList(new ArrayList<>());
    }

    //riceve un oggetto sondaggio contentente le informazioni ... sulla porta TCP
    //una volta ricevuto il sondaggio mandiamo un messaggio contentente l'ID del sondaggio sul gruppo multicast porta UDP e indirizzo 230.0.0.1
    public void riceviSondaggio(){
        try {
            ServerSocket serverSocket = new ServerSocket(portaTCP);
            while (true){
                Socket socket = serverSocket.accept();
                ServerHandler sh = new ServerHandler(socket, sondaggi);
                sh.start();
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    //riceve da un utente un id che rappresenta il sondaggio a cui vuole partecipare
    //il server risponde all'utente inviando l'oggetto sondaggio con quell'id
    //riceve un oggetto RisposteSondaggio che contiene le risposte al sondaggio
    public void compilazioneSondaggio(){
        //riceve quale sondaggio
        try{
            ServerSocket server = new ServerSocket(portTCP2);
            while(true) {
                Socket s = server.accept();
                GestisciPartecipazione g = new GestisciPartecipazione(s,risposteSondaggio,sondaggi);
                g.start();
            }
        }catch(IOException e){
            System.out.println(e);
        }
    }//compilazioneSondaggi

}//server

class ServerHandler extends Thread{
    private Socket socket;
    private Map<Sondaggio,Date> sondaggi;
    private final int portaUDP = 5000;
    private final String indirizzoGruppo = "230.0.0.1";

    public ServerHandler(Socket socket, Map<Sondaggio,Date> sondaggi){
        this.socket = socket;
    }

    public void run() {
        //riceviamo il sondaggio
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            Sondaggio sondaggio = (Sondaggio) ois.readObject();
            sondaggi.put(sondaggio, Calendar.getInstance().getTime());
            inviaATutti(sondaggio);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private void inviaATutti (Sondaggio sond) {
        try{
            MulticastSocket m = new MulticastSocket();
            String str = sond.getId()+" "+sond.getNome();
            byte [] buf = str.getBytes();
            InetAddress group = InetAddress.getByName(indirizzoGruppo);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, portaUDP);
            m.send(packet);
        }catch(IOException e){
            System.out.println(e);
        }
    }

}//ServerHandler

class GestisciPartecipazione extends Thread{
    private Socket socket;
    private List<RisposteSondaggio> risposteSondaggio;
    private Map<Sondaggio,Date> sondaggi;

    public GestisciPartecipazione(Socket socket, List<RisposteSondaggio> risposteSondaggio , Map<Sondaggio,Date> sondaggi ){
        this.socket = socket;
        this.risposteSondaggio = risposteSondaggio;
        this.sondaggi = sondaggi;

    }
    public void run(){
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(socket.getOutputStream(), true);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            int idSondaggio = ois.readInt();
            //prendiamo l'ora
            boolean cond = condizioneOrario(sondaggi.get(idSondaggio));
            if (cond) {
                oos.writeObject(sondaggi.get(idSondaggio));     //mandiamo il sondaggio con quell'id
                riceviRisposte();
            }
            else {
                pw.println("Il sondaggio "+idSondaggio+" è scaduto");
                oos.writeObject(new ArrayList<>());
            }
            ois.close();
            oos.close();
            socket.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }

    private void riceviRisposte(){
        try{
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            RisposteSondaggio risp = (RisposteSondaggio) ois.readObject();
            risposteSondaggio.add(risp);
        }catch(IOException | ClassNotFoundException e){
            System.out.println(e);
        }
    }


    private boolean condizioneOrario(Date d){
        boolean orarioValido = true;
        int[] ora = restituisciOrario(Calendar.getInstance().getTime());
        int[] oraSondaggio = restituisciOrario(d);

        if(ora[0] == oraSondaggio[0]){
            orarioValido = false;
        } else if (ora[0]-oraSondaggio[0]!=1) {
            orarioValido = false;
        } else if (ora[0]-oraSondaggio[0]==1 && ora[1] >= oraSondaggio[1]) {
            orarioValido = false;
        }

        return orarioValido;
    }


    private int[] restituisciOrario(Date d){
        String [] s = d.toString().split(" ");
        String time = s[3];
        int [] res = new int[2];
        String [] oraCompleta = time.toString().split(":");
        int ore = Integer.parseInt(oraCompleta[0]);
        int minuti = Integer.parseInt(oraCompleta[1]);
        res[0] = ore;
        res[1] = minuti;
        return res;
    }
}








