package openapi.tests;

import com.github.javafaker.Faker;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.data.DataSupplier;
import ge.tbc.testautomation.steps.integration.IntegrationFlowSteps;
import ge.tbc.testautomation.steps.openapi.AuthSteps;
import ge.tbc.testautomation.util.AllureRestAssuredFilterSetup;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import local.spring.api.AuthenticationV1CookiesBasedApi;
import local.spring.invoker.ApiClient;
import local.spring.model.AuthenticationResponse;
import local.spring.model.ErrorResponse;
import local.spring.model.RefreshTokenResponse;
import local.spring.model.RegisterUserRequest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Feature("local spring api")

public class SpringTests {
    private ApiClient apiClient;
    private AuthenticationV1CookiesBasedApi authApi;
    private Faker faker;
    private AuthSteps authSteps;

    @BeforeClass
    public void setup() {
        AllureRestAssuredFilterSetup.setup();
        apiClient = ApiClient.api(ApiClient.Config.apiConfig()
                .reqSpecSupplier(() -> new RequestSpecBuilder()
                        .setBaseUri("http://localhost:8086")
                        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                )
        );
        authApi = apiClient.authenticationV1CookiesBased();
        faker = new Faker();
        authSteps = new AuthSteps(authApi, "http://localhost:8086");
    }
    @Description("validate successful adminn registration")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void successfulRegistrationAndAccess() {
        String email = faker.internet().emailAddress();
        AuthenticationResponse response = authSteps.registerAdminUser(email);
        authSteps.validateProtectedMessage(response.getAccessToken());
    }

    @Description("validate that when typing invalid passwords error message shows")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "invalidPasswords", dataProviderClass = DataSupplier.class)
    public void registrationShouldFailWithInvalidPasswords(String invalidPassword) {
        RegisterUserRequest request = new RegisterUserRequest()
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(invalidPassword)
                .role(RegisterUserRequest.RoleEnum.ADMIN);

        Response resp = authApi.register1()
                .body(request)
                .execute(r -> r.then().statusCode(400).extract().response());

        Map<String, String> errorMap = resp.as(new TypeRef<>() {});

        assertThat(errorMap).containsKey("password");
        assertThat(errorMap.get("password"))
                .isEqualTo(Constants.PASS_ERROR);
    }
    @Description("refresh token adn check if old token still valida")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void refreshTokenFlow() {
        String email = faker.internet().emailAddress();
        AuthenticationResponse registered = authSteps.registerAdminUser(email);
        AuthenticationResponse authenticated = authSteps.authenticate(email, authSteps.testPassword);
        authSteps.validateAuthorities(authenticated);

        RefreshTokenResponse refreshed = authSteps.refreshAccessToken(authenticated.getRefreshToken());

        authSteps.validateProtectedMessage(refreshed.getAccessToken());
    }


}
