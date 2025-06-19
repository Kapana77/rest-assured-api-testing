package tests;

import com.github.javafaker.Faker;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.data.models.escuelajs.Login;
import ge.tbc.testautomation.data.models.escuelajs.LoginResponse;
import ge.tbc.testautomation.data.models.escuelajs.User;
import ge.tbc.testautomation.data.models.escuelajs.UserReq;
import ge.tbc.testautomation.steps.AuthSteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.UUID;

public class EscuelaJSTest {
    AuthSteps steps = new AuthSteps();
    Faker faker = new Faker();

    String email;
    String password;
    String name;
    String role;
    String avatar;

    @BeforeSuite
    public void setup() {
        RestAssured.baseURI = Constants.ESC_BASEURI;
    }

    @Test
    public void fullUserFlow() {
        email = faker.internet().emailAddress();
        password = faker.internet().password();
        name = faker.name().fullName();
        role = "customer";
        avatar = Constants.AVATAR;

        UserReq userRequest = new UserReq(email, name, password, role, avatar);
        Response createResp = steps.createUser(userRequest);
        steps.validateUserCreated(createResp);

        Login loginRequest = new Login(email, password);
        Response loginResp = steps.login(loginRequest);
        LoginResponse token = loginResp.as(LoginResponse.class);

        Response profileResp = steps.getProfile(token.getAccessToken());
        steps.validateName(profileResp,name)
                .validateMail(profileResp,email);
    }
}
