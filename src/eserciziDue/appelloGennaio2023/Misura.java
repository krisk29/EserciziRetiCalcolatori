package eserciziDue.appelloGennaio2023;

import java.io.Serializable;
import java.util.Date;

public class Misura implements Serializable {

    private String IDSensore;
    private double valoreMisurato;
    private Date timeStamp;

    public Misura(String IDSensore, double valoreMisurato, Date timeStamp) {
        this.IDSensore = IDSensore;
        this.valoreMisurato = valoreMisurato;
        this.timeStamp = timeStamp;
    }

    public String getIDSensore() {
        return IDSensore;
    }

    public double getValoreMisurato() {
        return valoreMisurato;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

}
