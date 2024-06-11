package eserciziUno.appelloGennaio2023;


import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;


public class TourOperatorService {

    private HashMap<String, HashMap<Integer, HashSet<String>>> prenotazioni; // nomeStruttura - <anno, Set<codiceUtente>>
    private HashMap<Struttura,Integer> str; //Struttura-postiOccupati


    //1. un metodo che, dato il nome della struttura, restituisce il numero di persone che hanno prenotato quella struttura nell'anno
    // corrente (anno in cui viene inviata la richiesta).

        public int numeroPersone(String nomeStruttura){
            return prenotazioni.get(nomeStruttura).get(Calendar.getInstance().get(Calendar.YEAR)).size();
        }

    //2.	un metodo che data una città e un numero di stelle, restituisce la struttura (se disponibile) di quella città,
    // avente un numero di stelle pari a quelle richieste, che dispone del minor numero di posti liberi al momento della richiesta.
    public Struttura miglioreStruttura(String città ,int numStelle)  {
            int anno = Calendar.YEAR;
            int min = Integer.MAX_VALUE;
            Struttura ret = null;
            String retNome = null;
            HashMap<String,Integer> nomePosti = creaMappa(città,numStelle); //nomeStruttura-postiTotali
            for(String nome: prenotazioni.keySet()){
                if(prenotazioni.get(nome).containsKey(anno)){
                    int occupati = prenotazioni.get(nome).get(anno).size();
                    int liberi = nomePosti.get(nome)-occupati;
                    if(liberi < min){
                        min = liberi;
                        retNome = nome;
                    }
                }
            }
            for(Struttura struttura: str.keySet()){
                if(struttura.getNome()==retNome){
                    ret = struttura;
                }
            }
            return ret;
    }

    private HashMap<String,Integer> creaMappa(String città, int numStelle) {
            HashMap<String,Integer> ret = new HashMap<>();
            for(Struttura struttura: str.keySet()){
                if(struttura.getCittà()==città && struttura.getStelle()==numStelle){
                        ret.put(struttura.getNome(),struttura.getPosti());
                }
            }
            return ret;
    }


}

