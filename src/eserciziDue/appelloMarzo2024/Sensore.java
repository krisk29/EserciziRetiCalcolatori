package eserciziDue.appelloMarzo2024;

import java.net.Socket;

public class Sensore {

    private int id;
    private int suolo;
    private int aria;

    public Sensore(int id) {
        this.id = id;
    }

    public StatoSensore ritornaStato(){
        return new StatoSensore(id,0,aria,suolo);
    }


}
