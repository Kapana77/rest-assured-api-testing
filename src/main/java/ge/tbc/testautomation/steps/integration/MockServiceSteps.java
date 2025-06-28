package ge.tbc.testautomation.steps.integration;

import ge.tbc.testautomation.data.Constants;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class MockServiceSteps {

    public Response registerUser(){
        return given()
                .baseUri(Constants.BASE_URI)
                .contentType("application/json")
                .body(Constants.REQ_BODY)
                .when()
                .post(Constants.POST_API);
    }
    public MockServiceSteps validateResp(Response response){
        response.then()
                .statusCode(200)
                .body("accessToken", notNullValue());
        return this;
    }
}
