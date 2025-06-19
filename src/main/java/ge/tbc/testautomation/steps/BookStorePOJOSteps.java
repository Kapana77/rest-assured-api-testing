package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.data.models.books.Book;
import ge.tbc.testautomation.data.models.books.ResponseBook;
import io.restassured.RestAssured;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class BookStorePOJOSteps {
    public ResponseBook getAllBooks() {
        return RestAssured
                .given()
                .when()
                .get(Constants.BOOK_FULL)
                .then()
                .statusCode(200)
                .extract()
                .as(ResponseBook.class);
    }

    public BookStorePOJOSteps validateAllBooksHaveLessThan(int maxPages, List<Book> books) {
        for (Book book : books) {
            assertThat(book.getPages(), lessThan(maxPages));
        }
        return this;
    }

    public BookStorePOJOSteps validateLastTwoAuthors(List<Book> books, String expectedLastAuthor, String expectedSecondLastAuthor) {
        int size = books.size();
        assertThat(books.get(size - 1).getAuthor(), equalTo(expectedLastAuthor));
        assertThat(books.get(size - 2).getAuthor(), equalTo(expectedSecondLastAuthor));
        return this;
    }
}
