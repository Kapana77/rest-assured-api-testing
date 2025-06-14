package ge.tbc.testautomation.data.models.books;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponseBook {
    @JsonProperty("books")
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }
}
