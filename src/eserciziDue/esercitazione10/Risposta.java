package eserciziDue.esercitazione10;

import java.io.Serializable;

public class Risposta implements Serializable {

    private int idProdotto;
    private int quantita;
    private double prezzoTot;
    private int idVenditore;

    public Risposta(int idProdotto, int quantita, double prezzoTot, int idVenditore) {
        this.idProdotto = idProdotto;
        this.quantita = quantita;
        this.prezzoTot = prezzoTot;
        this.idVenditore = idVenditore;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public double getPrezzoTot() {
        return prezzoTot;
    }

    public int getIdVenditore() {
        return idVenditore;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public void setPrezzoTot(double prezzoTot) {
        this.prezzoTot = prezzoTot;
    }

    public void setIdVenditore(int idVenditore) {
        this.idVenditore = idVenditore;
    }

    @Override
    public String toString() {
        return "Risposta{" +
                "idProdotto=" + idProdotto +
                ", quantita=" + quantita +
                ", prezzoTot=" + prezzoTot +
                ", idVenditore=" + idVenditore +
                '}';
    }
}
