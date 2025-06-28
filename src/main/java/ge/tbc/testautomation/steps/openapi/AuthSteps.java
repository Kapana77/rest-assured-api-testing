package ge.tbc.testautomation.steps.openapi;

import com.github.javafaker.Faker;
import ge.tbc.testautomation.data.Constants;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import local.spring.api.AuthenticationV1CookiesBasedApi;
import local.spring.api.AuthorizationApi;
import local.spring.model.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.error.ShouldHave.shouldHave;
import static pet.store.v3.invoker.ResponseSpecBuilders.shouldBeCode;
import static pet.store.v3.invoker.ResponseSpecBuilders.validatedWith;

public class AuthSteps {

    private final AuthenticationV1CookiesBasedApi authApi;
    private final String baseUri;
    private final Faker faker = new Faker();
    public String testEmail;
    public final String testPassword = "Admin@123";



    public AuthSteps(AuthenticationV1CookiesBasedApi authApi, String baseUri) {
        this.authApi = authApi;
        this.baseUri = baseUri;
    }

    @Step("Register user with ADMIN role and email: {email}")
    public AuthenticationResponse registerAdminUser(String email) {
        testEmail = email;

        RegisterUserRequest request = new RegisterUserRequest()
                .firstname("adminname")
                .lastname("adminlastname")
                .email(email)
                .password(testPassword)
                .role(RegisterUserRequest.RoleEnum.ADMIN);

        AuthenticationResponse response = authApi.register1()
                .body(request)
                .executeAs(validatedWith(shouldBeCode(200)));

        return response;
    }

    @Step("Authenticate with email and password")
    public AuthenticationResponse authenticate(String email, String password) {
        LoginRequest request = new LoginRequest()
                .email(email)
                .password(password);

        return authApi.authenticate1()
                .body(request)
                .execute(res -> res.then().statusCode(200).extract().as(AuthenticationResponse.class));
    }

    @Step("Validate user has all expected authorities")
    public void validateAuthorities(AuthenticationResponse response) {
        List<String> roles = response.getRoles();
        assertThat(roles).containsExactlyInAnyOrder(
                Constants.READ, Constants.WRITE, Constants.DELETE,Constants.UPDATE, Constants.ADM
        );
    }

    @Step("Call protected resource with token")
    public void validateProtectedMessage(String accessToken) {
        AuthorizationApi protectedApi = AuthorizationApi.authorization(() -> new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Accept", "text/plain")
        );

        Response response = protectedApi
                .sayHelloWithRoleAdminAndReadAuthority()
                .execute(r -> r.then()
                        .statusCode(200)
                        .extract()
                        .response());

        String message = response.getBody().asString();

        assertThat(message).contains(Constants.VALIDATION_MSG);
    }

    @Step("Refresh token using refresh_token")
    public RefreshTokenResponse refreshAccessToken(String refreshToken) {
        RefreshTokenRequest request = new RefreshTokenRequest()
                .refreshToken(refreshToken);

        return authApi.refreshToken1()
                .body(request)
                .execute(r -> r.then()
                        .statusCode(200)
                        .extract()
                        .as(RefreshTokenResponse.class));
    }
}
