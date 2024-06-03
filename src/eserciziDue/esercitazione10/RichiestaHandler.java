package eserciziDue.esercitazione10;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class RichiestaHandler extends Thread{
    private Socket socket;
    private List<Venditore> venditori;
    private List<Risposta> risposte;

    public RichiestaHandler(Socket socket,List<Venditore> venditori ) {
        this.socket = socket;
        this.venditori = venditori;
    }
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
