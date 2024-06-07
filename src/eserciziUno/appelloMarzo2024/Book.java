package eserciziUno.appelloMarzo2024;

import java.io.Serializable;

public class Book implements Serializable {
    private String ISBN;
     private String title;
     private String author;
     private String genere;
     private float prezzo;

     public Book(String ISBN, String title, String author, String genere, float prezzo) {
         this.ISBN = ISBN;
         this.title = title;
         this.author = author;
         this.genere = genere;
         this.prezzo = prezzo;
     }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenere() {
        return genere;
    }

    public float getPrezzo() {
        return prezzo;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN='" + ISBN + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genere='" + genere + '\'' +
                ", prezzo=" + prezzo +
                '}';
    }
}
