package eserciziDue.garaAppalti;

import java.io.Serializable;

public class Offerta implements Serializable {

    private int id;
    private int importo;

    public Offerta(int id, int importo) {
        this.id = id;
        this.importo = importo;
    }

    public int getId() {
        return id;
    }

    public int getImporto(){
        return importo;
    }

}
