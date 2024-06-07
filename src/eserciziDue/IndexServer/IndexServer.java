package eserciziDue.IndexServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexServer {

    private final int portUDP = 3000;
    private final int portTCP = 4000;
//facciamo finta che la keyword sia una e non una lista per semplicità
    private Map<String,String> salvataggi;

    public IndexServer(){
        salvataggi = new HashMap<String,String>();
    }

    public void riceviAttributi(){
        DatagramSocket datagramSocket = null;
        try{
          datagramSocket = new DatagramSocket(3000);
          byte[] buf = new byte[256];
          DatagramPacket packet = new DatagramPacket(buf, buf.length);
          datagramSocket.receive(packet);
          String dati = new String(packet.getData());
          String [] parser = dati.split(" ");
          salvataggi.put(parser[0],parser[1]);
        }catch(IOException e) {
            System.out.println(e);
        }
    }
}
