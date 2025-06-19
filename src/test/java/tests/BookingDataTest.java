package tests;

import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.data.DataSupplier;
import ge.tbc.testautomation.data.models.booking.Booking;
import ge.tbc.testautomation.data.models.requests.booking.BookingRequest;
import ge.tbc.testautomation.data.models.responses.booking.BookingResponse;
import ge.tbc.testautomation.steps.BookingSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class BookingDataTest extends BaseTest {
    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = Constants.BOOKER_URL;
    }

    private BookingSteps bookingSteps  = new BookingSteps();

    @Description("data driven tests for booking")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "bookingData",dataProviderClass = DataSupplier.class)
    public void bookingDataDrivenTest(BookingRequest booking) {
        String token = bookingSteps.createToken("admin", "password123");

        Booking createBody = bookingSteps.createBody();

        int bookingId = bookingSteps.createBooking(createBody);


        Response response = bookingSteps.updateBooking(bookingId, token, booking);
        bookingSteps.validateUpdateSuccessful(response)
                        .logResponse(response);

        BookingResponse bookingDeserialized = bookingSteps.deserializeBooking(response);

        assertThat(bookingDeserialized.getFirstname(),notNullValue());
        assertThat(bookingDeserialized.getLastname(),notNullValue());
        assertThat(bookingDeserialized.getFirstname(),equalTo(booking.getFirstname()));



    }

}
