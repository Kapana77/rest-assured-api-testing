package tests;

import ge.tbc.testautomation.data.BookingRequests;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.steps.RestfulBookerSteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class RestfulBookerTest {

    @Test
    public void updateBook(){
        RestAssured.baseURI = Constants.BOOKER_URL;

        RestfulBookerSteps steps = new RestfulBookerSteps();
        BookingRequests requests = new BookingRequests();

        String token = steps.createToken("admin", "password123");

        JSONObject createBody = requests.buildBooking(Constants.NAME, Constants.LAST_NAME, 99999, true,
                Constants.NEEDS,requests.buildBookingDates(Constants.CHECKIN, Constants.CHECKOUT)
        );

        int bookingId = steps.createBooking(createBody); //exTRACt id


        JSONObject updateBody = requests.buildBooking(Constants.UPDATED_NAME, Constants.UPDATED_LAST_NAME, 88999,
                true, Constants.UPDATED_NEEDS,
                requests.buildBookingDates(Constants.UPDATED_CHECKIN, Constants.UPDATED_CHECKOUT)
        );

        Response response = steps.updateBooking(bookingId, token, updateBody);

        steps.validateUpdateSuccessful(response)
                .logResponse(response);
    }

}

