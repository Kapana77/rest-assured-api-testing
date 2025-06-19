package ge.tbc.testautomation.data.models.books;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Book {

    private String isbn;
    private String title;
    @JsonProperty("subTitle")
    private String subtitle;
    private String author;
    @JsonProperty("publish_date")
    private String publishDate;
    private String publisher;
    private int pages;
    private String description;
    private String website;

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getPages() {
        return pages;
    }

    public String getDescription() {
        return description;
    }

    public String getWebsite() {
        return website;
    }
}
