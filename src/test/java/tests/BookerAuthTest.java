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

import java.time.LocalDate;

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
        Booking booking = new Booking();
        booking.setFirstname(faker.name().firstName());
        booking.setLastname(faker.name().lastName());
        booking.setTotalprice(150);
        booking.setDepositpaid(true);
        booking.setBookingdates(new BookingDates(LocalDate.now(),LocalDate.now().plusDays(2)));
        booking.setAdditionalneeds(Constants.BREAKFST);

        bookingId = steps.createBooking(booking);
        Response bookingResponse = steps.getBooking(bookingId);

        steps.assertBookingFirstName(bookingResponse, booking)
                .assertBookingLastName(bookingResponse, booking)
                .assertBookingPrice(bookingResponse, booking)
                .assertBookingDeposit(bookingResponse, booking)
                .assertBookingCheckin(bookingResponse, booking)
                .assertBookingCheckOut(bookingResponse, booking)
                .assertBookingNeeds(bookingResponse, booking);
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
