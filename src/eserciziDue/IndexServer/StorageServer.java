package eserciziDue.IndexServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StorageServer {

    private final int portAscolto = 2000;
    private List<File> files;

    public StorageServer() {
        this.files = Collections.synchronizedList(new ArrayList<File>());
    }

    public List<File> getFiles() {
        return files;
    }


    public void riceviDaClient(){
        try {
            ServerSocket serverSocket = new ServerSocket(2000);
            while(true){
                Socket socket = serverSocket.accept();
                StorageHandler s = new StorageHandler(socket,files);
                s.start();
            }
        }catch(IOException e){
            System.out.println("Errore");
        }
        }

}

class StorageHandler extends Thread {
    private Socket socket;
    private List<File> files;

    public StorageHandler(Socket socket, List<File> files) {
        this.socket = socket;
        this.files = files;
    }

    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            while (true) {
                try {
                    File f = (File) ois.readObject();
                    if (f == null) {
                        break; // Termina la lettura se riceviamo un segnale di fine (null)
                    }
                    files.add(f);
                    inviaDati(f);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    break;
                }
            }
            ois.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void inviaDati(File f) {
        try {
            DatagramSocket s = new DatagramSocket();
            String file = f.getFileName()+" "+f.getKeywords().toString();
            byte[] buf =file.getBytes();
            DatagramPacket packet = new DatagramPacket(buf,buf.length, InetAddress.getByName("localhost"),3000);
            s.send(packet);
        }
        catch (IOException e) {

        }
    }

    public static void main(String[] args) {
        // Avvio del server in un thread separato
        StorageServer server = new StorageServer();
        new Thread(server::riceviDaClient).start();

        try {
            // Attendere qualche secondo per dare il tempo al server di avviarsi
            Thread.sleep(2000);

            // Simulare un client che invia due file
            Socket socket = new Socket("localhost", 2000);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            ArrayList<String> keywords = new ArrayList<>();
            keywords.add("keyword1");
            keywords.add("keyword2");

            File file = new File("testFile.txt", keywords);
            oos.writeObject(file);
            oos.flush();

            ArrayList<String> keywords2 = new ArrayList<>();
            keywords2.add("keyword1rwe");
            keywords2.add("keywordesdf2");

            File file2 = new File("betA.txt", keywords2);
            oos.writeObject(file2);
            oos.flush();

            // Invia un segnale di fine (null)
            oos.writeObject(null);
            oos.flush();
            oos.close();
            socket.close();

            // Attendere qualche secondo per dare il tempo al server di elaborare la richiesta
            Thread.sleep(2000);

            // Stampare i file ricevuti dal server
            System.out.println("Files received by server:");
            for (File f : server.getFiles()) {
                System.out.println(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

