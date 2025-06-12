package tests;

import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.steps.BookStoreSteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class MoreBookStoreTests {
    private BookStoreSteps bookStoreSteps;

    @BeforeSuite
    public void setup() {
        RestAssured.baseURI =Constants.BOOKSTORE_URL;
        bookStoreSteps = new BookStoreSteps();
    }

    @Test
    public void validateBooksPagesAndAuthors() {
        Response response = bookStoreSteps.getAllBooks();

        bookStoreSteps
                .validateAllBooksPages(response, 1000)
                .validateAuthorOfBook(response, 0, Constants.EXP_AUTHOR1)
                .validateAuthorOfBook(response, 1, Constants.EXP_AUTHOR2);
    }


}
