package tests;

import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.util.Logging;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PetstoreTest {
    private static final Logger logger = Logging.getLogger(PetstoreTest.class);
    @BeforeMethod
    public void setUp(){
        RestAssured.baseURI = Constants.PETSTORE_BASEURI;

    }

    @Test
    public void postWithJson() {
        String orderBody = Constants.ORDER_BODY;
        Response response = given()
//                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .body(orderBody)
                .when().post(Constants.PATH_ORDER);

        response.then().statusCode(200);
        assertThat(response.path(Constants.PATH_ID), equalTo(Constants.EXPECTED_ID));
        assertThat(response.path(Constants.PATH_PETID), equalTo(Constants.EXPECTED_PET_ID));
        assertThat(response.path(Constants.PATH_STATUS), equalTo(Constants.EXPECTED_STATUS));
        assertThat(response.path(Constants.PATH_SHIPDATE), equalTo(Constants.EXPECTED_DATE));
    }
    @Test
    public void notFoundErrorPOST(){
        given()
                .pathParam("petId",Constants.INVALID_PETID)
                .formParam("name", Constants.PARAM_NAME)
                .formParam("status", Constants.PARAM_STATUS)
                .when()
                .post("/pet/{petId}")
                .then()
                .statusCode(404)
                .body("code", equalTo(404))
                .body("type", equalTo(Constants.EXPECTED_TYPE))
                .body("message", equalTo(Constants.EXPECTED_MSG));
    }

    @Test
    public void formPostReq() {

        given()
                .pathParam("petId", Constants.VALID_PETID)
                .formParam("name", Constants.PETNAME)
                .formParam("status", Constants.STATUS)
                .when()
                .post("/pet/{petId}")
                .then()
                .statusCode(200)
                .body("code", equalTo(200))
                .body("type", notNullValue())
                .body("message", equalTo(String.valueOf(Constants.VALID_PETID)))
                .log().all();
    }

    @Test
    public void triggerNotFoundErrorGET() {
        Response response = given()
                .when().get(Constants.INVALID_GET);

        assertEquals(response.getStatusCode(), 404);
        assertEquals(response.jsonPath().getString("message"), Constants.NOT_FOUND_MSG);
    }

    @Test
    public void loginGetReq() {
        Response response = given()
                .queryParam("username", Constants.USERNAME)
                .queryParam("password", Constants.PASSWORD)
                .when().get(Constants.LOGIN);

        response.then().statusCode(200);

        String message = response.path("message");
        String token = message.replaceAll("\\D+", ""); //significant numbers >10

        assertTrue(token.length() >= 10);
        logger.info(token);

    }
}
