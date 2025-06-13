package ge.tbc.testautomation.steps;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RestfulBookerSteps {
    public String createToken(String username, String password) {
        JSONObject authBody = new JSONObject();
        authBody.put("username", username);
        authBody.put("password", password);

        return given()
                .contentType(ContentType.JSON)
                .body(authBody.toString())
                .when()
                .post("/auth")
                .then()
                .extract().path("token");
    }

    public int createBooking(JSONObject body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when()
                .post("/booking")
                .then()
                .extract().path("bookingid");
    }

    public Response updateBooking(int bookingId, String token, JSONObject body) {
        return given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(body.toString())
                .when()
                .put("/booking/" + bookingId);
    }

    public RestfulBookerSteps validateUpdateSuccessful(Response response) {
        assertThat(response.statusCode(), equalTo(200));
        return this;
    }

    public RestfulBookerSteps logResponse(Response response) {
        response.then().log().all();
        return this;
    }

}
