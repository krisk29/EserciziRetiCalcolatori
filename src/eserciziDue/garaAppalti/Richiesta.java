package eserciziDue.garaAppalti;

public class Richiesta {

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
