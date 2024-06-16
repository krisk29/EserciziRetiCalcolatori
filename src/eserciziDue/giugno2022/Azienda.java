package eserciziDue.giugno2022;

import java.io.*;
import java.net.*;

public class Azienda {
    private String partitaIVA;

    public Azienda(String partitaIVA) {
        this.partitaIVA = partitaIVA;
    }

    public void inviaOfferta(Offerta offerta) {
        try (Socket socket = new Socket("job.unical.it", 3000);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            oos.writeObject(offerta);
            int idOfferta = ois.readInt();
            System.out.println("Offerta inviata con ID: " + idOfferta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Azienda azienda = new Azienda("123456789");
        Offerta offerta = new Offerta("S1", "Developer", "Full-time", 50000);
        azienda.inviaOfferta(offerta);
    }
}

