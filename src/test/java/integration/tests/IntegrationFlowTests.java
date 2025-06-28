package integration.tests;

import asd.newproject.second.*;
import com.github.javafaker.Faker;
import ge.tbc.testautomation.db.models.Employee;
import ge.tbc.testautomation.db.models.User;
import ge.tbc.testautomation.steps.integration.IntegrationEmployeeSteps;
import ge.tbc.testautomation.steps.integration.IntegrationFlowSteps;
import ge.tbc.testautomation.util.Marshall;
import ge.tbc.testautomation.util.Unmarshall;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import local.spring.model.AuthenticationResponse;
import local.spring.model.ChangeEmailRequest;
import local.spring.model.ErrorResponse;
import local.spring.model.LoginRequest;
import org.newproject.FindPersonResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.config.DataBaseConfig.dbMapper;
import static ge.tbc.testautomation.data.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class IntegrationFlowTests {
    private IntegrationFlowSteps steps = new IntegrationFlowSteps();
    private IntegrationEmployeeSteps soapSteps = new IntegrationEmployeeSteps();
    private String JWTToken;
    private String refreshToken;
    private Faker faker = new Faker();
    private String email;
    private Long id;


    @BeforeClass
    public void initialize(){
        steps.setup();
        email = faker.internet().emailAddress();
    }
    @Description("add employee and register")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 1)
    public void addEmployeeAndRegisterTest(){
        EmployeeInfo employeeinfo = steps.createEmployee(NAME,55L,DEP,UPDATED_ADDRESS,
                email);
        AddEmployeeRequest addEmployeeRequest = steps.createAddEmployeeRequest(employeeinfo);
        String body = Marshall.marshallSoapRequest(addEmployeeRequest);
        Response resp = steps.sendService(LOCAL_URL,ADD_ACT,body);
        AddEmployeeResponse employeeResponse = Unmarshall.unmarshallResponse(resp.body().asString(), AddEmployeeResponse.class);

        steps.validateRespMessage(employeeResponse)
                .validateRespStatus(employeeResponse);

    }
    @Description("try registering with invalid mail")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority =2 )
    public void negativeRegisterTest(){

        ErrorResponse resp = steps.registerInvalidUser(UNKN_EMAIL);
        steps.validateSoapErrorMessage(resp)
                .validateSoapErrorStatus(resp);

    }
    @Description("do valid registrattion ")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 3)
    public void validRegisterTest(){

        AuthenticationResponse authenticationResponse = steps.registerUserValidEmail(email,STRONG_PASS);
        id = authenticationResponse.getId();
        JWTToken = authenticationResponse.getAccessToken();
        refreshToken = authenticationResponse.getRefreshToken();
        steps.validateProtectedMessage(JWTToken);

    }
    @Test(priority = 4)
    public void changePasswordTest(){

        Response resp = steps.changePassword(STRONG_PASS,NEW_PASS,JWTToken);
        steps.validatePassChanged(resp);

    }
    @Test(priority = 5)
    public void logOutTest(){
        refreshToken = steps.logindAndGetRefreshToken(email,NEW_PASS);
        steps.logout(refreshToken);

        ErrorResponse err = steps.invaidRefreshToken(refreshToken);
        steps.validateError(err,refreshToken);

        LoginRequest loginRequest = steps.createLogin(email,NEW_PASS);
        refreshToken = steps.getAccessToken(loginRequest);

    }
    @Test(priority = 6)
    public void changeEmailTest(){
        String newmail =  "newmailafsdweqw@gmail.com";
        ChangeEmailRequest changeEmailRequest = steps.makeChangeEmailRequest(newmail);
        steps.changeEmail(changeEmailRequest,JWTToken);

        LoginRequest loginRequest = steps
                .createLogin(newmail,NEW_PASS);
        AuthenticationResponse authResponse = steps.authenticateV2(loginRequest);

        steps.validateResponseToken(authResponse)
                .validateResponseMail(authResponse,newmail);

    }
    @Description("validate employee was added with soap")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 7)
    public void validateWithSoap(){
        GetEmployeeByIdRequest req = soapSteps.createGetEmployeeByIdReq(55L);
        String body = Marshall.marshallSoapRequest(req);
        Response resp = soapSteps.sendService(LOCAL_URL, GETBYID_ACT, body);

        GetEmployeeByIdResponse employeeByIdResponse = Unmarshall.unmarshallResponse(resp.body().asString(), GetEmployeeByIdResponse.class);
        soapSteps.validateEmplMail(employeeByIdResponse,email);

    }
    @Description("validate employee was updated with DataBase")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 8)
    public void validateWithDatabase(){
        User user = dbMapper().selectUserByMail(email);
        Employee employee = dbMapper().selectById(55L);
        assertThat(employee.getEmail(),equalTo(email));
        assertThat(user.getEmail(),equalTo(email));

    }


}
