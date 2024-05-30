package eserciziUno.esercitazione10;

import java.io.Serializable;
import java.util.List;

public class ListaProdotto implements Serializable {

    List<Prodotto> prodotti;

    public ListaProdotto(){
        this.prodotti = prodotti;
    }

    public List<Prodotto> getProdotti() {
        return prodotti;
    }

    public String toString() {
       StringBuilder sb = new StringBuilder();
       for(Prodotto p : this.prodotti){
           sb.append(p.toString());
       }
       return sb.toString();
    }
}
