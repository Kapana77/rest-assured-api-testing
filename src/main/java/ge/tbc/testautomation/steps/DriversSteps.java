package ge.tbc.testautomation.steps;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.data.models.f1.Driver;
import ge.tbc.testautomation.data.models.f1.MRDataResponse;
import ge.tbc.testautomation.util.Logging;
import io.restassured.response.Response;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DriversSteps {
    private static final Logger logger = Logging.getLogger(DriversSteps.class);
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public Response getDriversResponse() {
        return given()
                .baseUri(Constants.DRIVERS_BASE)
                .when()
                .get(Constants.DRIVERS_ERGAST)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    private MRDataResponse mapResponse(Response response) {
        try {
            return mapper.readValue(response.asString(), MRDataResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(Constants.ERR_MSG, e);
        }
    }

    public DriversSteps validateSeries(Response response) {
        MRDataResponse data = mapResponse(response);
        assertThat(data.getMrData().getSeries(), equalTo("f1"));
        return this;
    }

    public DriversSteps validateSeason(Response response) {
        MRDataResponse data = mapResponse(response);
        assertThat(data.getMrData().getDriverTable().getSeason(), equalTo("2025"));
        return this;
    }

    public DriversSteps validateTotalDriversCount(Response response) {
        MRDataResponse data = mapResponse(response);
        int expectedTotal = Integer.parseInt(data.getMrData().getTotal());
        assertThat(data.getMrData().getDriverTable().getDrivers().size(), equalTo(expectedTotal));
        return this;
    }

    public DriversSteps firstDriverBefore1990(Response response) {
        MRDataResponse data = mapResponse(response);
        Driver first = data.getMrData().getDriverTable().getDrivers().stream()
                .filter(d -> d.getDateOfBirth().compareTo("1990-01-01") < 0)
                .findFirst().orElseThrow();
        logger.info("First born before 1990: {} {}", first.getGivenName(), first.getFamilyName());
        assertThat(first.getGivenName(), notNullValue());
        assertThat(first.getFamilyName(), notNullValue());
        return this;
    }

    public DriversSteps validateDriversBornAfter2000Count(Response response, int minCount) {
        MRDataResponse data = mapResponse(response);
        List<Driver> filtered = data.getMrData().getDriverTable().getDrivers().stream()
                .filter(d -> d.getDateOfBirth().compareTo("2000-01-01") > 0)
                .toList();
        logger.info("Born after 2000: {}", filtered.size());
        assertThat(filtered.size(), greaterThanOrEqualTo(minCount));
        return this;
    }

    public DriversSteps validateFrenchDriversCount(Response response, int expectedCount) {
        MRDataResponse data = mapResponse(response);
        long count = data.getMrData().getDriverTable().getDrivers().stream()
                .filter(d -> "French".equals(d.getNationality())).count();
        assertThat((int) count, equalTo(expectedCount));
        return this;
    }

    public DriversSteps validateDriversWithFamilyNameAorB(Response response, int minCount) {
        MRDataResponse data = mapResponse(response);
        long count = data.getMrData().getDriverTable().getDrivers().stream()
                .filter(d -> d.getFamilyName().startsWith("A") || d.getFamilyName().startsWith("B"))
                .count();
        assertThat((int) count, greaterThanOrEqualTo(minCount));
        return this;
    }

    public DriversSteps validateBritishDriversAfter1990(Response response, int minCount) {
        MRDataResponse data = mapResponse(response);
        long count = data.getMrData().getDriverTable().getDrivers().stream()
                .filter(d -> "British".equals(d.getNationality()) && d.getDateOfBirth().compareTo("1990-01-01") > 0)
                .count();
        assertThat((int) count, greaterThanOrEqualTo(minCount));
        return this;
    }

    public DriversSteps validateDriversWpermnumberAndname(Response response, int minCount) {
        MRDataResponse data = mapResponse(response);
        List<Driver> filtered = data.getMrData().getDriverTable().getDrivers().stream()
                .filter(d ->
                        (d.getPermanentNumber() != null && Integer.parseInt(d.getPermanentNumber()) < 10) || (d.getFamilyName().length() > 7)
                )
                .toList();
        assertThat(filtered.size(), greaterThanOrEqualTo(minCount));
        return this;
    }

    public List<String> findDriversByNationality(Response response, String nationality) {
        MRDataResponse data = mapResponse(response);
        return data.getMrData().getDriverTable().getDrivers().stream()
                .filter(d -> nationality.equals(d.getNationality()))
                .map(d -> d.getGivenName() + " " + d.getFamilyName())
                .toList();
    }

}
