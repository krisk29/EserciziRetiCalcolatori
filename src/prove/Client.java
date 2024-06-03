package prove;

import java.io.*;
import java.net.Socket;

public class Client implements Serializable {

    public Client() {
    }

    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 8080);
            System.out.println("Connesso al server.");

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);

            out.println("Sussone");
            //out.println("Folino");

            boolean more = false;
            while (!more) {
                String line = in.readLine();
                if (line == null) {
                    more = true;
                } else {
                    System.out.println("Risposta dal server: " + line);
                    // Invia di nuovo il messaggio al server (per illustrare un loop)
                    out.println(line);
                }
            }

            s.close();
            System.out.println("Connessione chiusa.");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
