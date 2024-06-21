package eserciziUno.appelloAprile2023;

import java.io.Serializable;

public class IncassoProdotto implements Serializable {

    String nomeVino;
    int quantita;
    double importo;

    public IncassoProdotto(String nomeVino, int quantita, double importo) {
        this.nomeVino = nomeVino;
        this.quantita = quantita;
        this.importo = importo;
    }

    public String getNomeVino() {
        return nomeVino;
    }

    public void setNomeVino(String nomeVino) {
        this.nomeVino = nomeVino;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

}
