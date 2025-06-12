package ge.tbc.testautomation.data;

import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class DataSupplier {
    @DataProvider
    public Object[][] bookData() {
        List<Map<String, Object>> books = given()
                .when().get(Constants.BOOKS_BASEPATH)
                .then().statusCode(200)
                .extract().jsonPath()
                .getList("books");
        return books.stream()
                .map(book -> new Object[]{book.get("isbn"), book.get("author")})
                .toArray(Object[][]::new);
    }
}
