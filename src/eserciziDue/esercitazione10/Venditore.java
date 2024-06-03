package eserciziDue.esercitazione10;

import java.util.List;

public class Venditore {

    private int id;
    private String gAddress = "230.0.0.1";
    private static final int gPort = 6789;

    public Venditore(int id, String gAddress) {
        this.id = id;
        this.gAddress = gAddress;
    }

    public int getId() {
        return id;
    }

    public String getgAddress() {
        return gAddress;
    }

    public int getgPort() {
        return gPort;
    }

    public void setgAddress(String gAddress) {
        this.gAddress = gAddress;
    }
}
