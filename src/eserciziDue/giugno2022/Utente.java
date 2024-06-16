package eserciziDue.giugno2022;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Utente {
    private String nome;

    public Utente(String nome) {
        this.nome = nome;
    }

    public void riceviOfferte() {
        try (MulticastSocket socket = new MulticastSocket(6000)) {
            socket.joinGroup(InetAddress.getByName("230.0.0.1"));
            byte[] buffer = new byte[256];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Offerta ricevuta: " + received);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inviaCandidatura(int idOfferta, String curriculumLink) {
        try (Socket socket = new Socket("job.unical.it", 4000);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            writer.println(idOfferta + " " + curriculumLink);
            System.out.println("Candidatura inviata per l'offerta con ID: " + idOfferta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Utente utente = new Utente("Mario Rossi");
        new Thread(utente::riceviOfferte).start();

        // Simulate user input for demonstration
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci l'ID dell'offerta e il link del curriculum (separati da uno spazio): ");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");
        int idOfferta = Integer.parseInt(parts[0]);
        String curriculumLink = parts[1];

        utente.inviaCandidatura(idOfferta, curriculumLink);
    }
}
