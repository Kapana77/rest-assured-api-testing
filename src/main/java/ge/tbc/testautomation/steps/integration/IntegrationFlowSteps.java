package ge.tbc.testautomation.steps.integration;

import asd.newproject.second.*;
import ge.tbc.testautomation.data.models.escuelajs.Login;
import ge.tbc.testautomation.util.AllureRestAssuredFilterSetup;
import ge.tbc.testautomation.util.SoapServiceSender;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import local.spring.api.AuthenticationV2Api;
import local.spring.api.AuthorizationApi;
import local.spring.api.UserManagementApi;
import local.spring.invoker.ApiClient;
import local.spring.model.*;
import org.codehaus.groovy.runtime.dgmimpl.arrays.IntegerArrayGetAtMetaMethod;

import static ge.tbc.testautomation.data.Constants.*;
import static local.spring.invoker.ResponseSpecBuilders.shouldBeCode;
import static local.spring.invoker.ResponseSpecBuilders.validatedWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class IntegrationFlowSteps {
    ObjectFactory objectFactory = new ObjectFactory();
    private AuthenticationV2Api authApi;
    private UserManagementApi userManagementApi;
    private ApiClient apiClient;

    @Step("Setup API clients and Allure filters")
    public void setup() {
        AllureRestAssuredFilterSetup.setup();
        apiClient = ApiClient.api(ApiClient.Config.apiConfig()
                .reqSpecSupplier(() -> new RequestSpecBuilder()
                        .setBaseUri(BASE_LOCAL)
                        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                )
        );
        authApi = apiClient.authenticationV2();
        userManagementApi = apiClient.userManagement();
    }

    @Step("Create EmployeeInfo with firstName={0}, id={1}, department={2}, address={3}, email={4}")
    public EmployeeInfo createEmployee(String firstName, long id, String dep, String address, String email) {
        EmployeeInfo employeeInfo = objectFactory.createEmployeeInfo();
        employeeInfo.withEmployeeId(id)
                .withName(firstName)
                .withDepartment(dep)
                .withAddress(address)
                .withEmail(email);

        return employeeInfo;
    }

    @Step("Send SOAP service request to URL={0} with action={1}")
    public Response sendService(String url, String action, String body) {
        return SoapServiceSender.send(url, action, body)
                .then().log().all().extract().response();
    }

    @Step("Create AddEmployeeRequest for employee")
    public AddEmployeeRequest createAddEmployeeRequest(EmployeeInfo employeeInfo) {
        AddEmployeeRequest addEmployeeRequest = objectFactory.createAddEmployeeRequest();
        addEmployeeRequest.withEmployeeInfo(employeeInfo);
        return addEmployeeRequest;
    }

    @Step("Validate SOAP response message")
    public IntegrationFlowSteps validateRespMessage(AddEmployeeResponse addEmployeeResponse) {
        assertThat(addEmployeeResponse.getServiceStatus().getMessage(), equalTo(ADDED_MESSAGE));
        return this;
    }

    @Step("Validate SOAP response status")
    public IntegrationFlowSteps validateRespStatus(AddEmployeeResponse addEmployeeResponse) {
        assertThat(addEmployeeResponse.getServiceStatus().getStatus(), equalTo("SUCCESS"));
        return this;
    }

    @Step("Register invalid user with email: {0}")
    public ErrorResponse registerInvalidUser(String invalidMail) {
        RegisterUserRequest req = new RegisterUserRequest()
                .email(invalidMail)
                .firstname(NAME)
                .lastname(LAST_NAME)
                .password(STRONG_PASS)
                .role(RegisterUserRequest.RoleEnum.ADMIN);

        return authApi.register()
                .body(req)
                .execute(r -> r.then()
                        .statusCode(502)
                        .extract()
                        .as(ErrorResponse.class));
    }

    @Step("Validate SOAP error message")
    public IntegrationFlowSteps validateSoapErrorMessage(ErrorResponse err) {
        assertThat(err.getMessage(), equalTo(SOAP_ERROR));
        return this;
    }

    @Step("Validate SOAP error status")
    public IntegrationFlowSteps validateSoapErrorStatus(ErrorResponse err) {
        assertThat(err.getStatus(), equalTo(502));
        return this;
    }

    @Step("Register user with valid email: {0}")
    public AuthenticationResponse registerUserValidEmail(String email, String pass) {
        RegisterUserRequest req = new RegisterUserRequest()
                .email(email)
                .firstname("Luka")
                .lastname("Kap")
                .password(pass)
                .role(RegisterUserRequest.RoleEnum.ADMIN);

        return authApi.register()
                .body(req).executeAs(validatedWith(shouldBeCode(200)));
    }

    @Step("Call protected resource with token")
    public void validateProtectedMessage(String accessToken) {
        AuthorizationApi protectedApi = AuthorizationApi.authorization(() -> new RequestSpecBuilder()
                .setBaseUri(BASE_LOCAL)
                .addHeader("Authorization", "Bearer " + accessToken)
                .setAccept(ContentType.JSON)
        );

        String message = protectedApi
                .sayHelloWithRoleAdminAndReadAuthority()
                .execute(response -> {
                    response.then().statusCode(200);
                    return response.asString();
                });

        assertThat(message, equalTo(VALIDATEION_MSG2));
    }

    @Step("Change password")
    public Response changePassword(String oldPassword, String newPassword, String accessToken) {
        ChangePasswordRequest req = new ChangePasswordRequest()
                .oldPassword(oldPassword)
                .newPassword(newPassword);

        userManagementApi = UserManagementApi.userManagement(() -> new RequestSpecBuilder()
                .setBaseUri(BASE_LOCAL)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "*/*")
        );

        return userManagementApi.changePassword()
                .body(req)
                .execute(res -> res.then().statusCode(200).extract().response());
    }

    @Step("Validate password changed successfully")
    public IntegrationFlowSteps validatePassChanged(Response response) {
        String responseText = response.getBody().asString();
        assertThat(responseText.equals("Password changed successfully"), is(true));
        return this;
    }

    @Step("Login and get refresh token for email: {0}")
    public String logindAndGetRefreshToken(String mail, String password) {
        AuthenticationResponse response = authApi.authenticate()
                .body(new LoginRequest()
                        .email(mail)
                        .password(password))
                .execute(res -> res.then().statusCode(200).extract().as(AuthenticationResponse.class));

        String refreshToken = response.getRefreshToken();

        return refreshToken;
    }

    @Step("Logout by revoking refresh token")
    public IntegrationFlowSteps logout(String refreshToken) {
        RefreshTokenRequest request = new RefreshTokenRequest()
                .refreshToken(refreshToken);

        authApi.revokeToken()
                .body(request)
                .execute(res -> validatedWith(shouldBeCode(200)));
        return this;
    }

    @Step("Invalidate refresh token")
    public ErrorResponse invaidRefreshToken(String refreshToken) {
        RefreshTokenRequest req = new RefreshTokenRequest().refreshToken(refreshToken);

        return authApi.refreshToken()
                .body(req)
                .execute(res -> res.then()
                        .statusCode(403)
                        .extract()
                        .as(ErrorResponse.class));
    }

    @Step("Validate error message for token: {1}")
    public IntegrationFlowSteps validateError(ErrorResponse err, String accessToken) {
        String expectedMessage = String.format("Failed for [%s]: Refresh token does not exist", accessToken);
        assertThat(err.getMessage(), equalTo(expectedMessage));
        return this;
    }

    @Step("Create login request for email: {0}")
    public LoginRequest createLogin(String email, String password) {
        return new LoginRequest()
                .email(email)
                .password(password);
    }

    @Step("Get access token from login request")
    public String getAccessToken(LoginRequest loginRequest) {
        AuthenticationResponse response = authApi.authenticate()
                .body(loginRequest)
                .executeAs(validatedWith(shouldBeCode(200)));

        return response.getAccessToken();
    }

    @Step("Make change email request to new email: {0}")
    public ChangeEmailRequest makeChangeEmailRequest(String newMail) {
        return new ChangeEmailRequest().newEmail(newMail);
    }

    @Step("Change email")
    public IntegrationFlowSteps changeEmail(ChangeEmailRequest changeEmailRequest, String token) {
        userManagementApi = UserManagementApi.userManagement(() -> new RequestSpecBuilder()
                .setBaseUri(BASE_LOCAL)
                .addHeader("Authorization", "Bearer " + token)
                .setContentType(ContentType.TEXT)
                .addHeader("Accept", "*/*")
        );
        userManagementApi.changeEmail()
                .body(changeEmailRequest)
                .execute(res -> res);
        return this;
    }

    @Step("Authenticate with login request")
    public AuthenticationResponse authenticateV2(LoginRequest loginRequest) {
        return authApi.authenticate().body(loginRequest)
                .executeAs(validatedWith(shouldBeCode(200)));
    }

    @Step("Validate that response contains refresh token")
    public IntegrationFlowSteps validateResponseToken(AuthenticationResponse authenticationResponse) {
        assertThat(authenticationResponse.getRefreshToken(), is(notNullValue()));
        return this;
    }

    @Step("Validate that response email is {1}")
    public IntegrationFlowSteps validateResponseMail(AuthenticationResponse authenticationResponse, String mail) {
        assertThat(authenticationResponse.getEmail(), equalTo(mail));
        return this;
    }

}
