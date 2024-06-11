package eserciziDue.appelloGennaio2023;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private final int portaTCP = 3000;
    private final int portaUDP = 4000;
    private static final int portaMulticast = 5000;
    private final String indirizzoMulticast = "230.0.0.1";
    private Map<Misura, Long> misure = new ConcurrentHashMap<>(); // misura - data
    private List<String> idMisurazioniMorte = Collections.synchronizedList(new ArrayList<String>());

    public Server() {
        Random random = new Random();
        long timestamp1 = System.currentTimeMillis() - (10 * 60 * 1000) - 1000; // 10 minuti e 1 secondo fa
        long timestamp2 = System.currentTimeMillis() - (5 * 60 * 1000) - 1000; // 5 minuti e 1 secondo fa
        long timestamp3 = System.currentTimeMillis() - (15 * 60 * 1000) - 1000; // 15 minuti e 1 secondo fa

        misure.put(new Misura("sensore1", 25.5, new Date(timestamp1)), timestamp1);
        misure.put(new Misura("sensore2", 30.2, new Date(timestamp2)), timestamp2);
        misure.put(new Misura("sensore3", 22.7, new Date(timestamp3)), timestamp3);

        ControllaMisurazioni c = new ControllaMisurazioni(idMisurazioniMorte, misure);
        c.setDaemon(true);
        c.start();
    }

    /* Da gestire
     * Un sensore viene considerato non funzionante se non ha inviato alcuna misura negli ultimi 10 minuti. In questo caso, il Server invia, un messaggio sul gruppo multicast caratterizzato dalla porta 5000 e dall'indirizzo 230.0.0.1. Il messaggio conterrà gli ID dei sensori non funzionanti.
     * */

    /* primo metodo
    riceve da un client una stringa contenente l’id di un sensore.
    Restituisce sullo stesso socket un oggetto di tipo Misura contenente l'id del sensore, il valore misurato (un double) e
    il timestamp in cui la misura è stata acquisita */
    public void ricevi() {
        try {
            ServerSocket serverSocket = new ServerSocket(portaTCP);
            while (true) {
                Socket socket = serverSocket.accept();
                ServerHandler serverHandler = new ServerHandler(socket, misure);
                serverHandler.start(); // Avvia il thread per gestire il client
            }
        } catch (IOException e) {
            System.out.println("Errore durante l'apertura della porta del server: " + e.getMessage());
        }
    }

    /*  Secondo metodo
    riceve da un sensore un oggetto Misura e provvede a memorizzarlo in un’apposita struttura dati.
    Ogni sensore, in particolare, invia una misura al server ogni 5 minuti. */
    public void registraMisura() {
        try {
            DatagramSocket datagramSocket = new DatagramSocket(portaUDP);
            while (true) {
                DatagramPacket datagramPacket = new DatagramPacket(new byte[256], 256);
                datagramSocket.receive(datagramPacket); // aspettiamo che qualcuno si colleghi
                ByteArrayInputStream bais = new ByteArrayInputStream(datagramPacket.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Misura misura = (Misura) ois.readObject();
                misure.put(misura, System.currentTimeMillis());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Errore durante la ricezione della misura via UDP: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server();

        // Thread per gestire le richieste TCP dei client
        Thread tcpThread = new Thread(() -> server.ricevi());
        tcpThread.setDaemon(true);
        tcpThread.start();

        // Thread per gestire le richieste UDP dai sensori
        Thread udpThread = new Thread(() -> server.registraMisura());
        udpThread.setDaemon(true);
        udpThread.start();

        // Mantieni il server in esecuzione
        try {
            tcpThread.join();
            udpThread.join();
        } catch (InterruptedException e) {
            System.out.println("Errore nel mantenere il server in esecuzione: " + e.getMessage());
        }
    }
}

class ControllaMisurazioni extends Thread {

    private List<String> idMisurazioniMorte;
    private Map<Misura, Long> misure;

    public ControllaMisurazioni(List<String> misurazioni, Map<Misura, Long> misure) {
        this.idMisurazioniMorte = misurazioni;
        this.misure = misure;
    }

    public void run() {
        try {
            MulticastSocket multicastSocket = new MulticastSocket();
            InetAddress group = InetAddress.getByName("230.0.0.1");
            while (true) {
                for (Misura m : misure.keySet()) {
                    long tempoTrascorso = System.currentTimeMillis() - misure.get(m);
                    if (tempoTrascorso >= (10 * 60 * 1000)) { // 10 minuti
                        idMisurazioniMorte.add(m.getIDSensore());
                        System.out.println("Sensore " + m.getIDSensore() + " non funzionante: " + tempoTrascorso / (60 * 1000) + " minuti senza misurazione.");
                    }
                }
                if (!idMisurazioniMorte.isEmpty()) {
                    String messaggio = "Sensori non funzionanti: ";
                    for (String s : idMisurazioniMorte) {
                        messaggio += s + ", ";
                    }
                    byte[] buf = messaggio.getBytes();
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 5000);
                    multicastSocket.send(packet);
                    idMisurazioniMorte.clear(); // Clear the list after sending the message
                }
                System.out.println(idMisurazioniMorte.toString());
                // Aggiunto un breve ritardo per evitare un loop continuo
                Thread.sleep(1000); // Ritardo di 1 secondo
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Errore durante l'invio delle notifiche: " + e.getMessage());
        }
    }
}

class ServerHandler extends Thread {

    private Socket socket;
    private Map<Misura, Long> misure;

    public ServerHandler(Socket socket, Map<Misura, Long> misure) {
        this.socket = socket;
        this.misure = misure;
    }

    public void run() {
        try {
            // Lettura id sensore
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String idSensore = in.readLine();

            // Restituiamo l'oggetto misura con quell'id
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            for (Misura misura : misure.keySet()) {
                if (misura.getIDSensore().equals(idSensore)) {
                    out.writeObject(misura);
                    out.flush(); // Flush the output stream
                }
            }
            // Chiude la connessione con il client
            socket.close();
        } catch (IOException e) {
            System.out.println("Errore durante la gestione del client: " + e.getMessage());
        } finally {
            try {
                // Chiude la connessione con il client in caso di errore
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
            }
        }
    }

}

