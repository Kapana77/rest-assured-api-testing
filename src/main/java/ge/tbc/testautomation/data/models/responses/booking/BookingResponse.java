package ge.tbc.testautomation.data.models.responses.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import ge.tbc.testautomation.data.models.booking.BookingDates;
import lombok.Data;

@Data
@JsonPropertyOrder({ "price", "firstname", "lastname", "depositpaid", "dates", "additionalneeds" })
public class BookingResponse {
    private String firstname;
    private String lastname;
    @JsonProperty("totalprice")
    private int price;
    private boolean depositpaid;
    @JsonProperty("bookingdates")
    private BookingDates dates;
    private String additionalneeds;
}