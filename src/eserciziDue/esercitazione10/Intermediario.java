package eserciziDue.esercitazione10;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Intermediario {
        private List<Venditore> venditori;

        public Intermediario(List<Venditore> venditori ) {
            this.venditori = venditori;
        }

    public List<Venditore> getVenditori() {
        return venditori;
    }

    public static void main(String[] args){
        try{
            Intermediario i = new Intermediario(new ArrayList<Venditore>());
            ServerSocket socket = new ServerSocket(2345);
            while(true){
                Socket s = socket.accept();
                RichiestaHandler r = new RichiestaHandler(s,i.getVenditori());
                r.start();
            }

        }catch(IOException e){
            System.out.println(e);
        }
    }
}
