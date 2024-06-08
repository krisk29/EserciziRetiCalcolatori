package eserciziUno.appelloGiugno2023;

import java.util.ArrayList;
import java.util.Map;

public class BibliotecaService {

    Map<String,ArrayList<InfoBiblioteca>> bibliotecaProvincia;     // provincia - infoBiblioteche (perchè infoBiblioteca ha numPrestiti e condiceBiblioteca)
    Map<String,Map<String, ArrayList<String>>> prestitiPerUtenteNellaBiblioteca;    //biblioteca - (Utente - ISBN)


    public void prestito(String codiceBiblioteca, String ISBN, String codiceFiscale){
        //registriamo il prestito
        if(prestitiPerUtenteNellaBiblioteca.get(codiceBiblioteca).containsKey(codiceFiscale)){
            prestitiPerUtenteNellaBiblioteca.get(codiceBiblioteca).get(codiceFiscale).add(ISBN);
        }
        else{
            prestitiPerUtenteNellaBiblioteca.get(codiceBiblioteca).put(codiceFiscale, new ArrayList<>());
            prestitiPerUtenteNellaBiblioteca.get(codiceBiblioteca).get(codiceFiscale).add(ISBN);
        }
        //aumenta il numPrestito di infoBiblioteca
        for(String provincia: bibliotecaProvincia.keySet()){
            if(bibliotecaProvincia.get(provincia).contains(codiceBiblioteca)) {
                for(InfoBiblioteca info: bibliotecaProvincia.get(provincia)){
                    if(info.getCodiceBiblioteca().equals(codiceBiblioteca) && info.getISBN().equals(ISBN)){
                        info.aumentaNumPrestiti();
                    }
                }
            }
        }
    }

    //restituisce un oggetto contenente contenente il nome della biblioteca di quella provincia che ha registrato il massimo numero di prestiti per quel libro
    public InfoBiblioteca Biblioteca(String ISBN, String provincia){
        InfoBiblioteca ris = null;
        ArrayList<InfoBiblioteca> biblioteche = bibliotecaProvincia.get(provincia);
        ArrayList<InfoBiblioteca> perControllareMassimo = new ArrayList<>();
        for(InfoBiblioteca info: biblioteche){
            if(info.getISBN().equals(ISBN)){
                perControllareMassimo.add(info);
            }
        }
        ris = ritornaMassimo(perControllareMassimo);
        return ris;
    }

    private InfoBiblioteca ritornaMassimo(ArrayList<InfoBiblioteca> perControllareMassimo) {
        int max = 0;
        InfoBiblioteca ris = null;
        for(InfoBiblioteca info: perControllareMassimo){
            if(info.getNumPrestiti() > max){
                max = info.getNumPrestiti();
                ris = info;
            }
        }
        return ris;
    }

}
