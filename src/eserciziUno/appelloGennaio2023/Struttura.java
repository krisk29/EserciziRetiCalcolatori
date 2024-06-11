package eserciziUno.appelloGennaio2023;

import java.io.Serializable;

public class Struttura implements Serializable {
    private String nome;
    private String città;
    private int stelle;
    private int posti;

    public Struttura(String nome, String città, int stelle, int posti) {
        this.nome = nome;
        this.città = città;
        this.stelle = stelle;
        this.posti = posti;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCittà(){
        return this.città;
    }

    public int getStelle(){
        return this.stelle;
    }

    public int getPosti(){
        return this.posti;
    }

}
