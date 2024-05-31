package eserciziDue.provaServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSemplice {

    public static void main(String[] args) {

        try{
            Socket socket = new Socket("localhost", 8888);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Testo 1");
            out.println("Testo 2");
            out.println("Testo 3");
            out.println("exit");

            socket.close();
        }
        catch(IOException e){
            System.out.println(e);
        }

    }

}
