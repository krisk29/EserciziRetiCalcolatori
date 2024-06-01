
package prove;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Serializable {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Server in ascolto sulla porta 8080...");

            while (true) {
                Socket s = serverSocket.accept();
                System.out.println("Connessione accettata da: " + s.getInetAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);

                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println("Ricevuto dal client: " + line);
                    out.println("Server: " + line);
                }

                s.close();
                System.out.println("Connessione chiusa con: " + s.getInetAddress());
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
