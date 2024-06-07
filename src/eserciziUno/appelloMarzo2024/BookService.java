package eserciziUno.appelloMarzo2024;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class BookService {

    HashMap<String,ListBooks> base;

    public BookService(HashMap<String,ListBooks> base) {
        this.base = base;
    }

    //Il metodo accetta aut-gen-pre e restituisce una lista di libri scritti dall'autore che appartengono al genere e prezzo inferiore a pre
    public ListBooks searchBook(String autGenPre) {
        ListBooks book = new ListBooks();

        String [] parti = autGenPre.split("-");

        String auth=parti[0];
        String gen=parti[1];
        float pre=Float.parseFloat(parti[2]);

        ListBooks l = base.get(auth);
        for(Book b : l.getLibri()){
            if(b.getGenere()==gen && b.getPrezzo()<pre){
                book.add(b);
            }
        }
        return book;
    }

    //Aggiunge il libro solo se non esiste già un libro con lo stesso titolo prodotto dall'autore specificato nella base di dati. Inoltre non lo aggiunge
    //se esistono 10 libri dello stesso autore.
    public boolean addBook(Book book) {
            String auth= book.getAuthor();
            ListBooks l = base.get(auth);
            for(Book b : l.getLibri()){
                if(l.getLibri().size() < 10 ||(b.getAuthor()!=auth && b.getTitle()!=book.getTitle())){
                    l.add(b);
                    return true;
                }
            }
            return false;
    }
}
