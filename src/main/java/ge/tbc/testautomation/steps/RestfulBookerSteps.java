package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.data.models.booking.AuthBody;
import ge.tbc.testautomation.data.models.booking.AuthResponse;
import ge.tbc.testautomation.data.models.booking.Booking;
import ge.tbc.testautomation.data.models.booking.BookingResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RestfulBookerSteps {

    public String createToken(String username, String password) {
        AuthResponse auth = given()
                .contentType(ContentType.JSON)
                .body(new AuthBody(username, password))
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract()
                .as(AuthResponse.class);

        return auth.getToken();
    }

    public int createBooking(Booking bookingBody) {
        BookingResponse response = given()
                .contentType(ContentType.JSON)
                .body(bookingBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .extract()
                .as(BookingResponse.class);

        return response.getBookingid();
    }

    public Response updateBooking(int bookingId, String token, Booking bookingBody) {
        return given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(bookingBody)
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
