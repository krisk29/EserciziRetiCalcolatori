package eserciziUno.appelloAprile2023;

import java.util.ArrayList;
import java.util.HashMap;

public class AziendaService {

    private HashMap<Integer, ArrayList<IncassoProdotto>> vendite;       //azienda - lista degli incassi

    //registra le vendite dell'azienda, riceve i parametri e registra
    public void vendita(int idAzienda, String nomeVino, int quantita, double importo){
        if(vendite.containsKey(idAzienda)){
            vendite.get(idAzienda).add(new IncassoProdotto(nomeVino, quantita, importo));
        }
        else{
            ArrayList<IncassoProdotto> incasso = new ArrayList<>();
            incasso.add(new IncassoProdotto(nomeVino, quantita, importo));
            vendite.put(idAzienda, incasso);
        }

    }

    //dato l'id dell'azienda restituisce un oggetto contenente il nome del vino che ha realizzato maggiori incassi complessivi
    //la quantità venduta e l'importo totale incassato
    public IncassoProdotto maggioreIncasso(int idAzienda){
        IncassoProdotto incasso = null;
        double maxIncasso = 0;
        for(IncassoProdotto inc : vendite.get(idAzienda)){
            if (inc.getImporto() > maxIncasso){
                maxIncasso = inc.getImporto();
                incasso = inc;
            }
        }
        return incasso;
    }

}
