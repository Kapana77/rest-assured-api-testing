package ge.tbc.testautomation.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ge.tbc.testautomation.data.models.responses.planets.PlanetsDetails;
import ge.tbc.testautomation.data.models.responses.planets.PlanetsResponse;
import ge.tbc.testautomation.data.models.responses.planets.Result;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PlanetsSteps {

    @Step("get response of all planets")
    public Response getPlanets(){
        return given()
                .param("format","json")
                .baseUri("https://swapi.tech/api")
                .when()
                .get("/planets");
    }
    @Step("deserialize planets")

    public PlanetsResponse deserializePlanets(Response planets){
        return planets.body().as(PlanetsResponse.class);

    }
    @Step("get urls from response")

    public List<String> extractUrls(PlanetsResponse pl){


        List<String> urls = new ArrayList<>();

        pl.planetsResponse().stream().forEach(planet -> urls.add(planet.url()));  // add planets to the list

        return urls;
    }
    @Step("create hasmap mapping each planets details with its timestamp value")

    public HashMap<PlanetsDetails, LocalDateTime> mapPlanetsWithTimestamp(List<String> urls) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();


        HashMap<PlanetsDetails, LocalDateTime> planetTimestamps = new HashMap<>();

        for (String url : urls) {
            PlanetsDetails planet = mapper.readValue(RestAssured.get(url).then().extract().body().asString(),PlanetsDetails.class);

            LocalDateTime timestamp = planet.timestamp();

            planetTimestamps.put(planet, timestamp);
        }

        return planetTimestamps;

    }
    @Step("get top 3 most recent planets with timestamp")

    public   List<Map.Entry<PlanetsDetails, LocalDateTime>> getTop3(HashMap<PlanetsDetails, LocalDateTime> planetTimestamps){
        return  planetTimestamps.entrySet().stream()
                .sorted(HashMap.Entry.<PlanetsDetails, LocalDateTime>comparingByValue().reversed())
                .limit(3)
                .toList();

    }
    @Step("create hasmap mapping resul of planet with its rotation")

    public Map<Result, Integer> planetsRotationMap(List<String> urls) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();


        Map<Result, Integer> planetRotationMap = new HashMap<>();

        for (String url : urls) {
            Response res = RestAssured.get(url);
            String json = res.getBody().asString();

            PlanetsDetails details = mapper.readValue(json, PlanetsDetails.class);
            Result result = details.result();
            String rotation = result.properties().getRotationPeriod();

            planetRotationMap.put(result, Integer.parseInt(rotation));
        }
        return planetRotationMap;


    }
    @Step("sort the values to get top1")

    public  List<Map.Entry<Result, Integer>> getSortedValues(Map<Result, Integer> planetRotationMap){
        return planetRotationMap.entrySet().stream()
                .sorted(Map.Entry.<Result, Integer>comparingByValue().reversed())
                .toList();

    }






}
