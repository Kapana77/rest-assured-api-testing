package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.data.models.booking.Booking;
import ge.tbc.testautomation.data.models.booking.BookingDates;
import ge.tbc.testautomation.data.models.requests.booking.BookingRequest;
import ge.tbc.testautomation.data.models.responses.booking.BookingResponse;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;

public class BookingSteps extends RestfulBookerSteps{

    @Step("Deserialize Booking")
    public BookingResponse deserializeBooking(Response response){
        return response.then()
                .statusCode(200)
                .extract().body().as(BookingResponse.class);
    }
    @Step("create reqeuest body for booking")
    public Booking createBody(){

        return new Booking().setFirstname(Constants.NAME)
                .setLastname(Constants.NAME)
                .setTotalprice(999)
                .setDepositpaid(true)
                .setBookingdates(new BookingDates(LocalDate.now(), LocalDate.now().plusDays(1)))
                .setAdditionalneeds(Constants.NEEDS);


    }




}
