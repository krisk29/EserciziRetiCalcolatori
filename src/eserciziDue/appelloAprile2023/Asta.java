package eserciziDue.appelloAprile2023;

import java.io.Serializable;

public class Asta implements Serializable {
    private int id;
    private String nome;


    public Asta(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }
    public String getNome(){
        return nome;
    }


}
