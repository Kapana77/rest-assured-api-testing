package tests;

import com.github.javafaker.Faker;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.data.models.booking.Booking;
import ge.tbc.testautomation.data.models.booking.BookingDates;
import ge.tbc.testautomation.steps.BookerAuthSteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class BookerAuthTest {
    Faker faker = new Faker();
    BookerAuthSteps steps = new BookerAuthSteps();
    String token;
    int bookingId;

    @BeforeSuite
    public void setup() {
        RestAssured.baseURI = Constants.BOOKER_URL;
        token = steps.getToken("admin", "password123");
    }

    @Test(priority = 1)
    public void createBookingTest() {
        Booking request = new Booking();
        request.setFirstname(faker.name().firstName());
        request.setLastname(faker.name().lastName());
        request.setTotalprice(150);
        request.setDepositpaid(true);
        request.setBookingdates(new BookingDates(Constants.CHECKIN, Constants.CHECKOUT));
        request.setAdditionalneeds(Constants.BREAKFST);

        bookingId = steps.createBooking(request);
        Response bookingResponse = steps.getBooking(bookingId);

        steps.assertBookingFirstName(bookingResponse, request)
                .assertBookingLastName(bookingResponse, request)
                .assertBookingPrice(bookingResponse, request)
                .assertBookingDeposit(bookingResponse, request)
                .assertBookingCheckin(bookingResponse, request)
                .assertBookingCheckOut(bookingResponse, request)
                .assertBookingNeeds(bookingResponse, request);
    }

    @Test(priority = 2)
    public void partiallyUpdateBookingTest() {
        steps.partialUpdateBooking(bookingId, token, Constants.UPDATED_NEEDS);
        Response updatedResponse = steps.getBooking(bookingId);
        updatedResponse.then().statusCode(200)
                .body("additionalneeds", equalTo(Constants.UPDATED_NEEDS));
    }

    @Test(priority = 3)
    public void deleteBookingTest() {
        steps.deleteBooking(bookingId, token)
                        .getBooking(bookingId).then().statusCode(404);
    }
}
