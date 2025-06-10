package tests;

import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.data.DataSupplier;
import ge.tbc.testautomation.util.Logging;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookstoreTest {
    private static final Logger logger = Logging.getLogger(BookstoreTest.class);


    @BeforeClass
    public void setup() {
        RestAssured.baseURI = Constants.BOOK_URL;
    }

    @Test
    public void getBooksandValidate() {
        Response response = given()
                .when()
                .get(Constants.BOOK_FULL)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();


        String firstBookIsbn =  response.jsonPath().getString("books[0].isbn");
        String firstBookAuthor = response.jsonPath().getString("books[0].author");

        String secondBookIsbn = response.jsonPath().getString("books[1].isbn");
        String secondBookAuthor = response.jsonPath().getString("books[1].author");

        logger.info("First Book:ISBN: {} Author: {}", firstBookIsbn, firstBookAuthor);
        logger.info("Second Book:ISBN: {} Author: {}", secondBookIsbn,secondBookAuthor);


        int size = response.jsonPath().getList("books").size();
        Assert.assertTrue(size > 2); // validate at least 2 because we are checking for first 2 books

        for (int i = 0; i < 2; i++) {
            response.then()
                    .body("books["+i+"].author", not(emptyOrNullString()))
                    .body("books["+i+"].isbn", not(emptyOrNullString()))
                    .body("books["+i+"].title", not(emptyOrNullString()))
                    .body("books["+i+"].publish_date", not(emptyOrNullString()))
                    .body("books["+i+"].pages", greaterThan(0));
        }
    }


    @Test(dataProvider = "bookData", dataProviderClass = DataSupplier.class)
    public void dataDriven(String isbn, String expectedAuthor) {
        Response response = given()
                .queryParam(Constants.ISBN, isbn)
                .when().get(Constants.BOOKSTORE)
                .then().statusCode(200)
                .extract().response();

        assertThat( response.path(Constants.PATH_AUTHOR), equalTo(expectedAuthor));
        assertThat(response.path(Constants.PATH_TITLE), notNullValue());
        assertThat(response.path(Constants.PATH_ISBN), equalTo(isbn));
        assertThat(response.path(Constants.PATH_DATE), notNullValue());
        assertThat(response.path(Constants.PATH_PAGES), notNullValue());
    }

    @Test
    public void deleteUnathorized() {
        given()
                .contentType(ContentType.JSON)
                .body(Constants.JSONBODY)
                .when().delete(Constants.BOOKSTORE)
                .then()
                .statusCode(401)
                .body("message", equalTo(Constants.UNAUTHORIZED_ERROR));
    }
}
