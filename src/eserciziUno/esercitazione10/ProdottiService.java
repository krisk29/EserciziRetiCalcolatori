package eserciziUno.esercitazione10;

import java.util.HashMap;

public class ProdottiService {

    private HashMap<String,HashMap<Prodotto,Integer>> venditeMagazzino; //idMagazzino - <Prodotto,vendita>

    //dato il nome del produttore restituisce il Prodotto più venduto di quel produttore tra i diversi magazzini
    public Prodotto ProdottoPiuVenduto(String produttore) {
        Prodotto prodottoPiuVenduto = null;
        int numVenditeMax = 0;
        for (String key : venditeMagazzino.keySet()) {
            for (Prodotto p : venditeMagazzino.get(key).keySet()){
                if (p.getNome().equals(produttore)){
                    if (numVenditeMax < venditeMagazzino.get(key).get(p)){
                        numVenditeMax = venditeMagazzino.get(key).get(p);
                        prodottoPiuVenduto = p;
                    }
                }
            }
        }
        return prodottoPiuVenduto;
    }

    //riceve id magazzino e restituisce una lista contenente i 3 prodotti che hanno prodotto più incassi per quel magazzino
    public ListaProdotto ProdottiMaxIncasso(String idMagazzino) {
        ListaProdotto prodottiPiuVenduti = new ListaProdotto();
        HashMap<Prodotto,Integer> magazzino = venditeMagazzino.get(idMagazzino);
        //giriamo la mappa, inseriamo nella linkedList il maggiore ed eliminiamo dalla mappa, poi trimmiamo la lista
        for(int i=0; i<3; i++){
            double incassoMax = 0;
            Prodotto prodottoMaxIncasso = null;
            for(Prodotto p : magazzino.keySet()){
                double incassoProdotto = p.getPrezzo()*magazzino.get(p);
                if (incassoMax < incassoProdotto){
                    incassoMax = incassoProdotto;
                    prodottoMaxIncasso = p;
                }
            }
            //prodottiPiuVenduti è un oggetto, prendiamola lista con get e con add aggiungiamo il prodotto
            prodottiPiuVenduti.getProdotti().add(prodottoMaxIncasso);
            magazzino.remove(prodottoMaxIncasso);
        }
        return prodottiPiuVenduti;
    }


}
