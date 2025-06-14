package tests;

import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.data.models.books.Book;
import ge.tbc.testautomation.data.models.books.ResponseBook;
import ge.tbc.testautomation.steps.BookStoreSteps;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.List;

public class MoreBookStoreTests {
    private BookStoreSteps bookStoreSteps;

    @BeforeSuite
    public void setup() {
        RestAssured.baseURI = Constants.BOOKSTORE_URL;
        bookStoreSteps = new BookStoreSteps();
    }

    @Test
    public void validateBooksPagesAndAuthors() {
        ResponseBook response = bookStoreSteps.getAllBooksAsPojo();
        List<Book> books = response.getBooks();

        bookStoreSteps
                .validateAllBooksPages(books, 1000)
                .validateAuthorOfBook(books, 0, Constants.EXP_AUTHOR1)
                .validateAuthorOfBook(books, 1, Constants.EXP_AUTHOR2);
    }


}
