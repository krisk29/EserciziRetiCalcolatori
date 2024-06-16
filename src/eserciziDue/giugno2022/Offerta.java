package eserciziDue.giugno2022;

import java.io.Serializable;

public class Offerta implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idOfferta;
    private String settore;
    private String ruolo;
    private String tipo;
    private double RAL;

    public Offerta(String settore, String ruolo, String tipo, double RAL) {
        this.settore = settore;
        this.ruolo = ruolo;
        this.tipo = tipo;
        this.RAL = RAL;
    }

    // Getters e setters

    public int getIdOfferta() {
        return idOfferta;
    }

    public void setIdOfferta(int idOfferta) {
        this.idOfferta = idOfferta;
    }

    public String getSettore() {
        return settore;
    }

    public void setSettore(String settore) {
        this.settore = settore;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getRAL() {
        return RAL;
    }

    public void setRAL(double RAL) {
        this.RAL = RAL;
    }
}

