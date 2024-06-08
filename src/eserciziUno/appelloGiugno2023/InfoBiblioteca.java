package eserciziUno.appelloGiugno2023;

import java.io.Serializable;

public class InfoBiblioteca implements Serializable {

    private String codiceBiblioteca;
    private String ISBN;
    private int numPrestiti;

    public InfoBiblioteca(String codiceBiblioteca, String ISBN, int numPrestiti) {
        this.codiceBiblioteca = codiceBiblioteca;
        this.ISBN = ISBN;
        this.numPrestiti = numPrestiti;
    }

    public String getCodiceBiblioteca() {
        return codiceBiblioteca;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getNumPrestiti() {
        return numPrestiti;
    }

    public void aumentaNumPrestiti() {
        numPrestiti++;
    }

    public String toString() {
        return "codidceBiblioteca: "+codiceBiblioteca+" ISBN: "+ISBN+" numPrestiti: "+numPrestiti;
    }

}
