package eserciziDue.provaServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConThread {

    public static void main(String[] args) {

        try{
            Socket socket = new Socket("localhost", 8888);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

           /* out.println("Testo 1");
            out.println("Testo 2");
            out.println("Testo 3");
           */
            for(int i=0; i<100000; i++){
                out.println("Testo numero: "+i);
            }

            //out.println("exit");

            boolean continua = true;
            while(continua){
                String s = in.readLine();
                if (s == null) {
                    continua = false;
                } else {
                    System.out.println(s);
                }
            }

            in.close();
            out.close();
            socket.close();
        }
        catch(IOException e){
            System.out.println(e);
        }

    }

}
