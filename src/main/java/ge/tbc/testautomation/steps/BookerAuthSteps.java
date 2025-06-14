package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.data.models.booking.Booking;
import ge.tbc.testautomation.data.models.booking.TokenRequest;
import ge.tbc.testautomation.data.models.booking.TokenResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BookerAuthSteps {

    public String getToken(String username, String password) {
        TokenRequest body = new TokenRequest(username, password);

        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract().as(TokenResponse.class)
                .getToken();
    }

    public int createBooking(Booking request) {
        return given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .extract().path("bookingid"); //extracts id
    }

    public Response getBooking(int id) {
        return given()
                .when()
                .get("/booking/" + id); //individual booking getter using id
    }


    public BookerAuthSteps assertBookingFirstName(Response response, Booking expected) {
        response.then().statusCode(200);
        assertThat(response.jsonPath().getString("firstname"), equalTo(expected.getFirstname()));
        return this;
    }
    public BookerAuthSteps assertBookingLastName(Response response, Booking expected) {
        response.then().statusCode(200);
        assertThat(response.jsonPath().getString("lastname"), equalTo(expected.getLastname()));
        return this;
    }
    public BookerAuthSteps assertBookingPrice(Response response, Booking expected) {
        response.then().statusCode(200);
        assertThat(response.jsonPath().getInt("totalprice"), equalTo(expected.getTotalprice()));
        return this;
    }
    public BookerAuthSteps assertBookingDeposit(Response response, Booking expected) {
        response.then().statusCode(200);
        assertThat(response.jsonPath().getBoolean("depositpaid"), equalTo(expected.isDepositpaid()));
        return this;
    }
    public BookerAuthSteps assertBookingCheckin(Response response, Booking expected) {
        response.then().statusCode(200);
        assertThat(response.jsonPath().getString("bookingdates.checkin"), equalTo(expected.getBookingdates().getCheckin()));
        return this;
    }
    public BookerAuthSteps assertBookingCheckOut(Response response, Booking expected) {
        response.then().statusCode(200);
        assertThat(response.jsonPath().getString("bookingdates.checkout"), equalTo(expected.getBookingdates().getCheckout()));
        return this;
    }
    public BookerAuthSteps assertBookingNeeds(Response response, Booking expected) {
        response.then().statusCode(200);
        assertThat(response.jsonPath().getString("additionalneeds"), equalTo(expected.getAdditionalneeds()));
        return this;
    }


    public void partialUpdateBooking(int bookingId, String token, String newNeed) {
        given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body("{\"additionalneeds\":\"" + newNeed + "\"}")
                .when()
                .patch("/booking/" + bookingId)
                .then()
                .statusCode(200);
    }

    public BookerAuthSteps deleteBooking(int bookingId, String token) {
        given()
                .cookie("token", token)
                .when()
                .delete("/booking/" + bookingId)
                .then()
                .statusCode(201);
        return this;
    }
}
