package ge.tbc.testautomation.steps;

import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookStoreSteps {

    public Response getAllBooks() {
        return io.restassured.RestAssured.given()
                .when()
                .get("/Books")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    public BookStoreSteps validateAllBooksPages(Response response, int maxPages) {
        assertThat(response.jsonPath().getList("books.pages"), everyItem(lessThan(maxPages)));
        return this;
    }

    public BookStoreSteps validateAuthorOfBook(Response response, int bookIndex, String expectedAuthor) {
        assertThat(response.jsonPath().getString("books[" + bookIndex + "].author"), equalTo(expectedAuthor));
        return this;
    }
}
