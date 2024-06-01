package eserciziDue.esercitazione10;

import java.io.Serializable;

public class Richiesta implements Serializable {
    private int id;
    private int quantita;

    public Richiesta(int id, int quantita) {
        this.id = id;
        this.quantita = quantita;
    }
    public int getId() {
        return this.id;
    }
    public int getQuantita(){
        return this.quantita;
    }

    public String toString(){
        return id + " " + quantita;
    }


}
