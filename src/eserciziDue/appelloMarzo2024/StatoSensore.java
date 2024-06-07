package eserciziDue.appelloMarzo2024;

import java.io.Serializable;
import java.util.Objects;

public class StatoSensore implements Serializable {
    private int idSensore;
    private int numProgressivo;
    private int aria;
    private int suolo;

    public StatoSensore(int idSensore, int numProgressivo, int aria, int suolo) {
        this.idSensore = idSensore;
        this.numProgressivo = numProgressivo;
        this.aria = aria;
        this.suolo = suolo;
    }

    public int getIdSensore() {
        return idSensore;
    }

    public int getNumProgressivo() {
        return numProgressivo;
    }

    public int getAria() {
        return aria;
    }

    public int getSuolo() {
        return suolo;
    }

    public void setIdSensore(int idSensore) {
        this.idSensore = idSensore;
    }

    public void setNumProgressivo(int numProgressivo) {
        this.numProgressivo = numProgressivo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatoSensore that)) return false;
        return idSensore == that.idSensore && numProgressivo == that.numProgressivo && aria == that.aria && suolo == that.suolo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSensore, numProgressivo, aria, suolo);
    }

    @Override
    public String toString() {
        return "StatoSensore{" +
                "idSensore=" + idSensore +
                ", numProgressivo=" + numProgressivo +
                ", aria=" + aria +
                ", suolo=" + suolo +
                '}';
    }
}
