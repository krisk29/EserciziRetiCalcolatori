package eserciziUno.appelloMarzo2024;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListBooks implements Serializable {

    private List<Book> libri;

    public ListBooks(List <Book> libri ) {
        this.libri = libri;
    }
    public ListBooks() {
        this.libri = new ArrayList<Book>();
    }

    public void add(Book b) {
        libri.add(b);
    }

    public List<Book> getLibri() {
        return libri;

    }


}
