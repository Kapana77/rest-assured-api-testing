package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.data.models.responses.planets.PlanetsDetails;
import ge.tbc.testautomation.data.models.responses.planets.PlanetsResponse;
import ge.tbc.testautomation.data.models.responses.planets.Properties;
import ge.tbc.testautomation.data.models.responses.planets.Result;
import ge.tbc.testautomation.steps.PlanetsSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanetsTests {
    PlanetsSteps planetsSteps = new PlanetsSteps();

    @Description("Deserialize the response and identify the three most recent objects in the list of results based on the `timestamp` field")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testPlanets() throws JsonProcessingException {

        Response planets = planetsSteps.getPlanets();

        PlanetsResponse pl = planetsSteps.deserializePlanets(planets);


        List<String> urls = planetsSteps.extractUrls(pl);


        HashMap<PlanetsDetails, LocalDateTime> planetTimestamps = planetsSteps.mapPlanetsWithTimestamp(urls);


        List<Map.Entry<PlanetsDetails, LocalDateTime>>top3 = planetsSteps.getTop3(planetTimestamps);

        top3.forEach(entry ->
                System.out.println(Constants.NAMES + entry.getKey().result().properties().getName()));
    }

    @Test
    public void rotationAndValidationTest() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();


        List<String> urls = planetsSteps.getPlanets().jsonPath().getList("results.url"); //second way of extracting urls
        Map<Result, Integer> planetRotationMap = planetsSteps.planetsRotationMap(urls);

        List<Map.Entry<Result, Integer>> sorted = planetsSteps.getSortedValues(planetRotationMap);

        Result topPlanet = sorted.get(0).getKey();
        Properties props = topPlanet.properties();


        Assert.assertNotNull(props.getName());
        Assert.assertEquals(props.getName(), Constants.EXPECTED_NAME);
        Assert.assertEquals(props.getTerrain(),Constants.EXPECTED_TERRAIN);
        Assert.assertEquals(props.getUrl(),Constants.EXPECTED_URL);


    }
}
