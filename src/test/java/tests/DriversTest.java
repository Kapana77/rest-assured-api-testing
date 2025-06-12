package tests;

import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.steps.DriversSteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DriversTest {
    DriversSteps driversSteps;
    Response response;

    @BeforeSuite
    public void setup() {
        RestAssured.baseURI = Constants.DRIVERS_BASE;
        driversSteps = new DriversSteps();
        response = driversSteps.getDriversResponse();
    }


    @Test
    public void validateSeasonSeries() {
        driversSteps.validateSeason(response)
                .validateSeries(response);
    }

    @Test
    public void validateTotalNumberDrivers(){
        driversSteps.validateTotalDriversCount(response);
    }

    @Test
    public void findDriversTest(){
        driversSteps.firstDriverBefore1990(response)
                .validateDriversBornAfter2000Count(response,8)
                .validateFrenchDriversCount(response,3)
                .validateDriversWithFamilyNameAorB(response,5)
                .validateBritishDriversAfter1990(response,3)
                .validateDriversWpermnumberAndname(response,5);
    }

    @Test
    public void validateDutchDriversIncludeVerstappen() {
        List<String> dutchDrivers = driversSteps.findDriversByNationality(response, Constants.DUTCH);
        assertThat(dutchDrivers.stream().anyMatch(name -> name.contains(Constants.EXPETCED_VERSTAPPEN)), is(true));
    }

    @Test
    public void validateAtLeastOneBrazilianDriver() {
        List<String> brazilianDrivers = driversSteps.findDriversByNationality(response, Constants.BRAZIL);
        assertThat(brazilianDrivers.size(), greaterThanOrEqualTo(1));
    }

    @Test
    public void validateCanadianDriversIncludeLanceStroll() {
        List<String> canadianDrivers = driversSteps.findDriversByNationality(response, Constants.CANADIAN);
        assertThat(canadianDrivers, hasItem(Constants.EXPECTED_ITEM));
    }



}
