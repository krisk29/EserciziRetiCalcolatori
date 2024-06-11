package eserciziDue.appelloSettebre2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class Utente {

    private long password;
    private int portTCP = 3000;
    private int id;

    public Utente(){}

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public void inviaRichiestaPrenotazione(String nomeCategoria) {
        Socket socket = null;
        try {
            System.out.println("Tipo di categoria che provo a inviare: " + nomeCategoria);
            socket = new Socket("localhost", portTCP);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(nomeCategoria);

        } catch (IOException e) {
            System.out.println("Errore dell'invio della categoria: " + e);
        }
    }

    public void riceviPasswordEAttendi(){
        try{
            Socket socket = new Socket("localhost", portTCP);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String stringaConIdEPassword = in.readLine();
            String[] stringhe = stringaConIdEPassword.split(":");
            setId(Integer.parseInt(stringhe[1]));
            password = Long.valueOf(stringhe[3]);

            MulticastSocket socketM = new MulticastSocket();
            InetAddress gruppo = InetAddress.getByName("230.0.0.1");
            socketM.joinGroup(gruppo);
            DatagramPacket packet = new DatagramPacket(new byte[256], 256);
        }
        catch(IOException e) {
        }
    }



    public static void main(String[] args) {
        Utente utente = new Utente();
        Utente utente2 = new Utente();
        Utente utente3 = new Utente();
        System.out.println("Categorie possibili: fiscale, catasto, scuola, sociale");
        utente.inviaRichiestaPrenotazione("scuola");
        utente2.inviaRichiestaPrenotazione("scuola");
        utente2.inviaRichiestaPrenotazione("scuola");
        utente2.inviaRichiestaPrenotazione("fiscale");
        utente3.inviaRichiestaPrenotazione("catasto");
        utente3.inviaRichiestaPrenotazione("sociale");
    }


}
