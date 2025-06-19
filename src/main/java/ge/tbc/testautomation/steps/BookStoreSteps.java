package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.data.models.books.Book;
import ge.tbc.testautomation.data.models.books.ResponseBook;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookStoreSteps {


    public ResponseBook getAllBooksAsPojo() {
        return given()
                .when()
                .get("/Books")
                .then()
                .statusCode(200)
                .extract().as(ResponseBook.class);
    }

    public BookStoreSteps validateAllBooksPages(List<Book> books, int maxPages) {
        assertThat(books, everyItem(hasProperty("pages", lessThan(maxPages))));
        return this;
    }

    public BookStoreSteps validateAuthorOfBook(List<Book> books, int index, String expectedAuthor) {
        assertThat(books.get(index).getAuthor(), equalTo(expectedAuthor));
        return this;
    }
}
