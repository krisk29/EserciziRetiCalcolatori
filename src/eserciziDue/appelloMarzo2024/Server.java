package eserciziDue.appelloMarzo2024;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private Map<Integer,ArrayList<StatoSensore>> stati;
    private final int portRic = 3000;
    private AtomicInteger numProg = new AtomicInteger(0) ;

    public Server(){
        this.stati = Collections.synchronizedMap(new HashMap<>());
    }

    public void riceviStatoSensore(){
        try {
            ServerSocket ss = new ServerSocket(portRic);
            while(true){
                Socket s = ss.accept();
                GestoreSensori g = new GestoreSensori(s,stati,numProg);
                g.start();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        }

}

class GestoreSensori extends Thread{
    private Socket s;
    private final LocalTime START_TIME = LocalTime.of(8, 0);
    private final LocalTime END_TIME = LocalTime.of(13, 0);
    private Map<Integer,ArrayList<StatoSensore>> stati;
    private AtomicInteger numProg;

    PrintWriter pw = null;
    ObjectInputStream ois=null;

    public GestoreSensori(Socket s,Map<Integer,ArrayList<StatoSensore>> stati ,AtomicInteger numProg ){
        this.s = s;
        this.stati=stati;
        this.numProg = numProg;
    }
    public void run(){
        try {
            pw = new PrintWriter(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());
            if (isWithinTimeFrame(LocalTime.now(), START_TIME, END_TIME)) {
                StatoSensore ss = (StatoSensore) ois.readObject();
                if (((ss.getSuolo() >= mediaSuolo(ss.getIdSensore()) - (mediaSuolo(ss.getIdSensore()) * 5 / 100)) && (ss.getSuolo() <= mediaSuolo(ss.getIdSensore()) + (mediaSuolo(ss.getIdSensore()) * 5 / 100)))
                        && ((ss.getAria() >= mediaAria(ss.getIdSensore()) - (mediaAria(ss.getIdSensore()) * 5 / 100)) && (ss.getAria() <= mediaAria(ss.getIdSensore()) + (mediaAria(ss.getIdSensore()) * 5 / 100)))){
                    pw.println("Errore, sei fuori range");
                }
                else{
                    ss.setNumProgressivo(numProg.getAndIncrement());
                    List<StatoSensore> sus = stati.get(ss.getIdSensore());
                    pw.println("Il numero è"+ss.getNumProgressivo());
                }
            }else {
                pw.println("Errore, sei fuori orario");
            }
        }catch(IOException | ClassNotFoundException e){
            System.out.println("Errore di riceviStatoSensore");
        }
    }

    private int mediaSuolo(int id){
        List<StatoSensore> statiSensori = stati.get(id);
        int count = 0;
        int media=0;
        for(StatoSensore s : statiSensori){
            media+=s.getSuolo();
            count++;
        }
        return media/count;
    }

    private int mediaAria(int id){
        List<StatoSensore> statiSensori = stati.get(id);
        int count = 0;
        int media=0;
        for(StatoSensore s : statiSensori){
            media+=s.getAria();
            count++;
        }
        return media/count;
    }

    private boolean isWithinTimeFrame(LocalTime currentTime, LocalTime startTime, LocalTime endTime) {
        return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
    }
    }
