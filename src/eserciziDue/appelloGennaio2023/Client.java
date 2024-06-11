package eserciziDue.appelloGennaio2023;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Client {

        public Client() {
        }

        public void inviaId(String idSensore) {
                try (Socket socket = new Socket("localhost", 3000);
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                        out.println(idSensore);
                } catch (IOException e) {
                        System.out.println(e);
                }
        }

        public Misura riceviMisurazione() {
                try (Socket socket = new Socket("localhost", 3000);
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                        return (Misura) in.readObject();
                } catch (IOException | ClassNotFoundException e) {
                        System.out.println(e);
                }
                return null;
        }

        public static void inviaMisura(Misura misura) {
                try {
                        DatagramSocket d = new DatagramSocket();
                        InetAddress address = InetAddress.getByName("localhost");
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(baos);
                        oos.writeObject(misura);
                        byte[] buf = baos.toByteArray();
                        DatagramPacket dp = new DatagramPacket(buf, buf.length, address, 4000);
                        d.send(dp);
                } catch (IOException e) {
                        System.err.println("Errore durante l'invio della misura: " + e.getMessage());
                }
        }

        public static void main(String[] args) {
                Client client = new Client();

                // Esempio di invio ID sensore al server e ricezione misura
                String idSensore = "sensore1";
                client.inviaId(idSensore);

                Misura misuraRicevuta = client.riceviMisurazione();
                if (misuraRicevuta != null) {
                        System.out.println("ID Sensore: " + misuraRicevuta.getIDSensore());
                        System.out.println("Valore: " + misuraRicevuta.getValoreMisurato());
                        System.out.println("Timestamp: " + misuraRicevuta.getTimeStamp());
                } else {
                        System.out.println("Misura non trovata per l'ID sensore richiesto.");
                }

                // Esempio di invio misura al server tramite UDP
                Misura misura = new Misura("sensore1", 25.5, new Date());
                Timer timer = new Timer();

                // Pianifica l'esecuzione del compito ogni 5 minuti
                timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                                Client.inviaMisura(misura);
                        }
                }, 0, 5 * 60 * 1000); // 5 minuti in millisecondi

                System.out.println("Misura inviata al server tramite UDP.");
        }
}
