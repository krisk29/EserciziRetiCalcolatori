package eserciziDue.appelloAprile2023;

import java.io.Serializable;

public class Offerta implements Serializable {
    private String codiceFiscale;
    private int idAsta;
    private int denaro;

    public Offerta(String codiceFiscale, int idAsta, int denaro) {
        this.codiceFiscale = codiceFiscale;
        this.idAsta = idAsta;
        this.denaro = denaro;
    }
    public String getCodiceFiscale() {
        return codiceFiscale;
    }
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }
    public int getIdAsta() {
        return idAsta;
    }
    public void setIdAsta(int idAsta) {
        this.idAsta = idAsta;
    }
    public int getDenaro() {
        return denaro;
    }
}
