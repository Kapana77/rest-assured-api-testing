package integration.tests;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.db.models.Employee;
import ge.tbc.testautomation.db.models.User;
import ge.tbc.testautomation.steps.integration.IntegrationFlowSteps;
import ge.tbc.testautomation.steps.integration.MockServiceSteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import local.spring.model.AuthenticationResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.*;
import org.yaml.snakeyaml.scanner.Constant;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static ge.tbc.testautomation.config.DataBaseConfig.dbMapper;
import static io.restassured.RestAssured.authentication;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class MockServiceTests {
    private MockServiceSteps mockServiceSteps = new MockServiceSteps();
    private IntegrationFlowSteps integrationFlowSteps = new IntegrationFlowSteps();

    private WireMockServer wireMockServer;

    @BeforeSuite
    public void setupWireMock() {
        wireMockServer = new WireMockServer(8087);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8087);


        stubFor(post(urlEqualTo("/ws"))
                .withHeader("Content-Type", containing("text/xml"))
                .withHeader("SoapAction", containing(Constants.EMPLBYMAIL))
                .withRequestBody(matchingXPath("//*[local-name()='email']"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/xml; charset=utf-8")
                        .withBodyFile("mock-GetEmployeeByEmail-response.xml")));
    }

    @AfterSuite
    public void teardownWireMock() {
        wireMockServer.stop();
    }

    @Test
    public void testUserRegistration_withMockedSOAP() {

        Response response = mockServiceSteps.registerUser();

        mockServiceSteps.validateResp(response);

        AuthenticationResponse res = integrationFlowSteps.registerUserValidEmail(Constants.RAND_GMAIL2,Constants.STRONG_PASS);
        String JWTToken = res.getAccessToken();

        verify(postRequestedFor(urlEqualTo("/ws"))
                .withRequestBody(containing(Constants.RAND_GMAIL2)));

        integrationFlowSteps.validateProtectedMessage(JWTToken);

        User user = dbMapper().selectUserByMail(Constants.RAND_GMAIL2);

        assertThat(user.getEmail(),equalTo(Constants.RAND_GMAIL2));
    }



//    private WireMockServer wireMockServer;
//
//    @BeforeClass
//    public void startMockServer() {
//        wireMockServer = new WireMockServer(8087);
//        wireMockServer.start();
//
//        wireMockServer.stubFor(post(urlEqualTo("/ws"))
//                .withHeader("Content-Type", containing("text/xml"))
//                .withRequestBody(containing("<getEmployeeByEmail"))
//                .willReturn(aResponse()
//                        .withStatus(HttpStatus.SC_OK)
//                        .withHeader("Content-Type", "text/xml;charset=UTF-8")
//                        .withBody("""
//                    <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
//                        <SOAP-ENV:Header/>
//                        <SOAP-ENV:Body>
//                            <ns2:getEmployeeByEmailResponse xmlns:ns2="http://interfaces.soap.springboot.example.com">
//                                <ns2:employeeInfo>
//                                    <ns2:employeeId>31</ns2:employeeId>
//                                    <ns2:name>Mocked User</ns2:name>
//                                    <ns2:department>MockDept</ns2:department>
//                                    <ns2:address>Mock Address</ns2:address>
//                                    <ns2:email>mockeduser@example.com</ns2:email>
//                                </ns2:employeeInfo>
//                            </ns2:getEmployeeByEmailResponse>
//                        </SOAP-ENV:Body>
//                    </SOAP-ENV:Envelope>
//                """)
//                ));
//    }
//
//    @AfterClass
//    public void stopMockServer() {
//        wireMockServer.stop();
//    }
//
//    @Test
//    public void testRegisterUserViaRestWithMockedSoap() {
//
//
//        String requestBody = """
//            {
//                "firstname": "MockFirst",
//                "lastname": "MockLast",
//                "email": "mockeduser@example.com",
//                "password": "StrongPass1234@!",
//                "role": "ADMIN"
//            }
//           """;
//
//        Response response = given()
//                .baseUri("http://localhost:8086")
//                .basePath("/api/v2/auth/register")
//                .contentType("application/json")
//                .body(requestBody)
//                .when()
//                .post()
//                .then()
//                .statusCode(200)
//                .extract().response();
//
//        String jwtToken = response.jsonPath().getString("accessToken");

//    }

}
