package tests;

import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.data.models.booking.Booking;
import ge.tbc.testautomation.data.models.booking.BookingDates;
import ge.tbc.testautomation.steps.RestfulBookerSteps;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class RestfulBookerTest {

    @Test
    public void updateBook() {
        RestAssured.baseURI = Constants.BOOKER_URL;

        RestfulBookerSteps steps = new RestfulBookerSteps();

        String token = steps.createToken("admin", "password123");

        Booking createBody = new Booking();

        createBody.setFirstname(Constants.NAME)
                .setLastname(Constants.NAME)
                .setTotalprice(999)
                .setDepositpaid(true)
                .setBookingdates(new BookingDates(LocalDate.now(), LocalDate.now().plusDays(1)))
                .setAdditionalneeds(Constants.NEEDS);

        int bookingId = steps.createBooking(createBody);

        Booking updateBody = new Booking(
                Constants.UPDATED_NAME,
                Constants.UPDATED_LAST_NAME,
                88999,
                true,
                new BookingDates(LocalDate.now(), LocalDate.now().plusDays(1)),
                Constants.UPDATED_NEEDS
        );

    }

}

