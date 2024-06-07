package eserciziDue.appelloGiugno2023;

import java.io.Serializable;
import java.util.ArrayList;

public class RisposteSondaggio implements Serializable {

    private ArrayList<Boolean> listaRisposte;

    public RisposteSondaggio() {
        listaRisposte = new ArrayList<>();
    }

    public ArrayList<Boolean> getListaRisposte() {
        return listaRisposte;
    }


}