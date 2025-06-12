package tests;

import ge.tbc.testautomation.data.Constants;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LibraryTest {

    @Test
    public void validateInfo() {
        given()
                .queryParam("q", Constants.SEARCH_KEYWORD)
                .when()
                .get(Constants.LIBRARY)
                .then()
                .statusCode(200)
                .assertThat()
                .body("docs", not(empty()),
                        "docs[0].title", equalTo(Constants.EXPECTED_TITLE), //will fail not true
                        "docs[0].author_name", hasItem(Constants.EXPECTED_AUTHOR),
                        "docs[0].place", hasItems(Constants.EXPECTED_PLACE1,
                                Constants.EXPECTED_PLACE2,Constants.EXPECTED_PLACE3)); //this also fails place is null
    }

}
