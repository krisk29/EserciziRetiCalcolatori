package eserciziUno.esercitazione10;

import java.io.Serializable;
import java.util.Objects;

public class Prodotto implements Serializable {

    private String codice;
    private String nome;
    private String produttore;
    private double prezzo;

    public Prodotto(String codice, String nome, String produttore, double prezzo) {
        this.codice = codice;
        this.nome = nome;
        this.produttore = produttore;
        this.prezzo = prezzo;
    }

    public String getCodice() {
        return codice;
    }

    public String getNome() {
        return nome;
    }

    public String getProduttore() {
        return produttore;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public String toString() {
        return codice + " " + nome + " " + produttore + " " + prezzo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prodotto prodotto = (Prodotto) o;
        return Double.compare(prezzo, prodotto.prezzo) == 0 && Objects.equals(codice, prodotto.codice) && Objects.equals(nome, prodotto.nome) && Objects.equals(produttore, prodotto.produttore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codice, nome, produttore, prezzo);
    }

    //nel codice dell'esercitatore ha fatto solo costruttore e getter

}
