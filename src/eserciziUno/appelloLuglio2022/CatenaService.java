package eserciziUno.appelloLuglio2022;

import java.util.HashMap;

public class CatenaService {

    private HashMap<String,HashMap<String,Integer>> incassoPerGiornoDelNegozio; //giorno - <Negozio - incasso>

    //data una cifra e una città, il metodo incrementa l’incasso giornaliero del negozio.
    public void incrementaGuadagni(int cifra, String citta){
        incassoPerGiornoDelNegozio.get(getCurrentDay()).put(citta,incassoPerGiornoDelNegozio.get(getCurrentDay()).get(citta)+cifra);
    }

    //un metodo che restituisce il nome della città del negozio in cui, rispetto al giorno precedente, si verifica la maggiore differenza di incasso.
    public String maxGuadagno2(){
        String giornoAttuale = getCurrentDay();
        String giornoPrecedente = getPreviousDay(giornoAttuale);
        int maxDifferenza = 0;
        String negozioMaxDifferenza = "";
        for(String città : incassoPerGiornoDelNegozio.get(giornoAttuale).keySet()){
            int differenza = (incassoPerGiornoDelNegozio.get(giornoAttuale).get(città))-(incassoPerGiornoDelNegozio.get(giornoPrecedente).get(città));
            if(differenza>maxDifferenza){
                maxDifferenza = differenza;
                negozioMaxDifferenza = città;
            }
        }
        return negozioMaxDifferenza;
    }

    private static String getCurrentDay() {
        return "Oggi";
    }

    private static String getPreviousDay(String date) {
        return "Ieri";
    }

}

