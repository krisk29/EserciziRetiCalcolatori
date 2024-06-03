package eserciziDue;


import java.io.Serializable;

public class Richiesta implements Serializable {
    private int id;
    private int quantita;


    public Richiesta(int id, int quantita) {
        this.id = id;
        this.quantita = quantita;
    }

    public int getId(){
        return id;

    }
    public int getQuantita(){
        return quantita;
    }

    public String toString(){
        return id + " " + quantita;
    }

}
