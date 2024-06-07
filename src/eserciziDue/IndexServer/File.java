package eserciziDue.IndexServer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class File implements Serializable {
    private String fileName;
    private ArrayList<String> keywords;

    public File(String fileName, ArrayList<String> keywords) {
        this.fileName = fileName;
        this.keywords = keywords;

    }

    public String getFileName() {
        return fileName;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileName='" + fileName + '\'' +
                ", keywords=" + keywords +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof File file)) return false;
        return Objects.equals(fileName, file.fileName) && Objects.equals(keywords, file.keywords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, keywords);
    }

}
