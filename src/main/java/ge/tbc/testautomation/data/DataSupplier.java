package ge.tbc.testautomation.data;

import com.github.javafaker.Faker;
import ge.tbc.testautomation.data.models.booking.BookingDates;
import ge.tbc.testautomation.data.models.requests.booking.BookingRequest;
import org.testng.annotations.DataProvider;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class DataSupplier {
    Faker faker = new Faker();
    @DataProvider
    public Object[][] bookData() {
        List<Map<String, Object>> books = given()
                .when().get(Constants.BOOKS_BASEPATH)
                .then().statusCode(200)
                .extract().jsonPath()
                .getList("books");
        return books.stream()
                .map(book -> new Object[]{book.get("isbn"), book.get("author")})
                .toArray(Object[][]::new);
    }

    @DataProvider
    public Object[][] bookingData(){
        Object[][] out = new Object[2][1];

        for(int i = 0; i < 2; ++i){
            out[i][0] = new BookingRequest()
                    .setFirstname(faker.name().firstName())
                    .setLastname(faker.name().lastName())
                    .setTotalprice(faker.number().numberBetween(1, 1000))
                    .setDepositpaid(faker.bool().bool())
                    .setBookingdates(
                            i == 0 ? new BookingDates(LocalDate.now(), LocalDate.now().plusDays(7))
                                    : new BookingDates(LocalDate.now(), LocalDate.now().plusWeeks(2))
                    )
                    .setAdditionalneeds(i == 0 ? "Breakfast" : "Dinner")
                    .setPassportNo(null);

        }
        return out;
    }
}
