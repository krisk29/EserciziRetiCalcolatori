package eserciziDue.garaAppalti;

import java.io.Serializable;

public class Richiesta implements Serializable {

    private String descrizione;
    private int importoMassimo;

    public Richiesta(String descrizione, int importoMassimo) {
        this.descrizione = descrizione;
        this.importoMassimo = importoMassimo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

}
