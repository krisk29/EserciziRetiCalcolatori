package eserciziDue.appelloGiugno2023;

import java.io.Serializable;
import java.util.List;

public class Sondaggio implements Serializable {
    private int id;
    private String nome;
    private List<String> listaDomande;


    public Sondaggio(int id, String nome, List<String> listaDomande) {
        this.id = id;
        this.nome = nome;
        this.listaDomande = listaDomande;
    }

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public List<String> getListaDomande() {
        return listaDomande;
    }


}
