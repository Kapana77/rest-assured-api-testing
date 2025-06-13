package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.util.Logging;
import io.restassured.response.Response;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class DriversSteps {
    private static final Logger logger = Logging.getLogger(DriversSteps.class);


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
    public DriversSteps validateSeries(Response response) {
        response.then()
                .body("MRData.series", equalTo("f1"));
        return this;
    }
    public DriversSteps validateSeason(Response response) {

        response.then()
                .body("MRData.DriverTable.season", equalTo("2025"));
        return this;
    }

    public DriversSteps validateTotalDriversCount(Response response) {
        int total = response.jsonPath().getInt("MRData.total");
        response.then()
                .body("MRData.DriverTable.Drivers.size()", equalTo(total));
        return this;
    }

    public DriversSteps firstDriverBefore1990(Response response) {
        String driverName = response.jsonPath()
                .getString("MRData.DriverTable.Drivers.find { it.dateOfBirth < '1990-01-01' }.givenName") +
                " " +
                response.jsonPath()
                        .getString("MRData.DriverTable.Drivers.find { it.dateOfBirth < '1990-01-01' }.familyName");

        response.then()
                .body("MRData.DriverTable.Drivers.find { it.dateOfBirth < '1990-01-01' }.givenName", notNullValue())
                .body("MRData.DriverTable.Drivers.find { it.dateOfBirth < '1990-01-01' }.familyName", notNullValue());

        logger.info("first was : {}", driverName);
        return this;


    }

    public DriversSteps validateDriversBornAfter2000Count(Response response, int minCount) {
        List<Map<String, String>> drivers = response.jsonPath()
                .getList("MRData.DriverTable.Drivers.findAll { it.dateOfBirth > '2000-01-01' }");

        List<String> driversAfter2000 = drivers.stream()
                .map(d -> d.get("givenName") + " " + d.get("familyName"))
                .toList();

        response.then()
                .body("MRData.DriverTable.Drivers.findAll { it.dateOfBirth > '2000-01-01' }.size()", greaterThanOrEqualTo(minCount));

        logger.info("born after 2000 : {}", driversAfter2000);
        return this;
    }

    public DriversSteps validateFrenchDriversCount(Response response, int expectedCount) {
        response.then()
                .body("MRData.DriverTable.Drivers.findAll { it.nationality == 'French' }.size()", equalTo(expectedCount));
        return this;
    }

    public DriversSteps validateDriversWithFamilyNameAorB(Response response, int minCount) {
        response.then()
                .body("MRData.DriverTable.Drivers.findAll { it.familyName ==~ /A.*/ || it.familyName ==~ /B.*/ }.size()", greaterThanOrEqualTo(minCount));
        return this;
    }

    public DriversSteps validateBritishDriversAfter1990(Response response, int minCount) {
        response.then()
                .body("MRData.DriverTable.Drivers.findAll { it.nationality == 'British' && it.dateOfBirth > '1990-01-01' }.size()", greaterThanOrEqualTo(minCount));
        return this;
    }

    public DriversSteps validateDriversWpermnumberAndname(Response response, int minCount) {
        List<Map<String, Object>> filteredDrivers = response.jsonPath()
                .getList("MRData.DriverTable.Drivers.findAll { it.permanentNumber.toInteger() < 10 || it.familyName.length() > 7 }");

        List<String> drivers = filteredDrivers.stream()
                .map(d -> d.get("givenName") + " " + d.get("familyName"))
                .toList();

        response.then()
                .body("MRData.DriverTable.Drivers.findAll { it.permanentNumber.toInteger() < 10 || it.familyName.length() > 7 }.size()", greaterThanOrEqualTo(minCount));

        return this;

    }

    public List<String> findDriversByNationality(Response response, String nationality) {
        List<Map<String, Object>> filteredDrivers = response.jsonPath()
                .getList("MRData.DriverTable.Drivers.findAll { it.nationality == '" + nationality + "' }");

        return filteredDrivers.stream()
                .map(driver -> driver.get("givenName") + " " + driver.get("familyName"))
                .toList();    }

}
