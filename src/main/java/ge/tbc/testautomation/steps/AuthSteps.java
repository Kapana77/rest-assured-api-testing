package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.data.models.escuelajs.Login;
import ge.tbc.testautomation.data.models.escuelajs.LoginResponse;
import ge.tbc.testautomation.data.models.escuelajs.User;
import ge.tbc.testautomation.data.models.escuelajs.UserReq;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AuthSteps {

    public Response createUser(UserReq request) {
        return given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/v1/users");
    }

    public Response login(Login loginRequest) {
        return given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/v1/auth/login");
    }

    public Response getProfile(String accessToken) {
        return given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/v1/auth/profile");
    }

    public AuthSteps validateUserCreated(Response response) {
        response.then().statusCode(201);
        return this;
    }


    public AuthSteps validateMail(Response response, String expectedEmail) {
        response.then().statusCode(200);
        assertThat(response.jsonPath().getString("email"), equalTo(expectedEmail));
        return this;
    }

    public AuthSteps validateName(Response response, String expectedName) {
        response.then().statusCode(200);
        assertThat(response.jsonPath().getString("name"), equalTo(expectedName));
        return this;
    }
}
