package tests;

import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.data.models.booking.Booking;
import ge.tbc.testautomation.data.models.booking.BookingDates;
import ge.tbc.testautomation.steps.RestfulBookerSteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class RestfulBookerTest {

    @Test
    public void updateBook() {
        RestAssured.baseURI = Constants.BOOKER_URL;

        RestfulBookerSteps steps = new RestfulBookerSteps();

        String token = steps.createToken("admin", "password123");

        Booking createBody = new Booking(
                Constants.NAME,
                Constants.LAST_NAME,
                99999,
                true,
                new BookingDates(Constants.CHECKIN, Constants.CHECKOUT),
                Constants.NEEDS
        );

        int bookingId = steps.createBooking(createBody);

        Booking updateBody = new Booking(
                Constants.UPDATED_NAME,
                Constants.UPDATED_LAST_NAME,
                88999,
                true,
                new BookingDates(Constants.UPDATED_CHECKIN, Constants.UPDATED_CHECKOUT),
                Constants.UPDATED_NEEDS
        );

        Response response = steps.updateBooking(bookingId, token, updateBody);
        steps.validateUpdateSuccessful(response)
                .logResponse(response);
    }

}

