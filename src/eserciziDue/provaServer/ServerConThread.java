package eserciziDue.provaServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConThread {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            while(true) {
                Socket socket = serverSocket.accept();
                socket.setSoTimeout(10000);
                ThreadHeandlerServer thread = new ThreadHeandlerServer(socket);
                thread.start();
            }
        }
        catch (IOException e) {
            System.out.println(e);
            System.out.println("Timeout del socket scaduto: " + e.getMessage());
        }
    }

}


class ThreadHeandlerServer extends Thread{
    Socket socket;

    ThreadHeandlerServer(Socket socket){
        this.socket = socket;
    }

    public void run() {
        boolean continua = true;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while (continua) {
                String line = in.readLine();
                if (line.equals("exit")) {   //quando viene stampato exit finisce tutto, altrimenti stampa ogni cosa che riceve
                    continua = false;
                }
                System.out.println("Messaggio: " + line);
                out.println("Ho ricevuto il tuo messaggio, era: " + line);
            }
            in.close();
            out.close();
            socket.close();
            System.out.println("Server chiuso.");
        } catch (IOException e) {
            System.out.println("Timeout del socket scaduto: " + e.getMessage());
            continua = false;
        }


    }

}