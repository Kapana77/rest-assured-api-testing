package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.data.models.booking.AuthBody;
import ge.tbc.testautomation.data.models.booking.AuthResponse;
import ge.tbc.testautomation.data.models.booking.Booking;
import ge.tbc.testautomation.data.models.booking.BookingResponse;
import ge.tbc.testautomation.data.models.requests.booking.BookingRequest;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RestfulBookerSteps {
    @Step("create token")
    public String createToken(String username, String password) {
        AuthResponse auth = given()
                .baseUri("https://restful-booker.herokuapp.com")
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
    @Step("Create Booking request body")
    public int createBooking(Booking bookingBody) {
        BookingResponse response = given()
                .baseUri("https://restful-booker.herokuapp.com")
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

    @Step("update booking with id : {bookingId}")

    public Response updateBooking(int bookingId, String token, BookingRequest bookingBody) {
        return given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(bookingBody)
                .when()
                .put("/booking/" + bookingId);
    }


    @Step("validate update was successfull")

    public RestfulBookerSteps validateUpdateSuccessful(Response response) {
        assertThat(response.statusCode(), equalTo(200));
        return this;
    }
    @Step("log response")
    public RestfulBookerSteps logResponse(Response response) {
        response.then().log().all();
        return this;
    }

}
