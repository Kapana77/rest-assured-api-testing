package integration.tests;

import asd.newproject.second.GetEmployeeByIdResponse;
import ge.tbc.testautomation.steps.integration.SoapDemoSteps;
import ge.tbc.testautomation.util.Marshall;
import ge.tbc.testautomation.util.Unmarshall;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.newproject.FindPerson;
import org.newproject.FindPersonResponse;
import org.testng.annotations.Test;
import static ge.tbc.testautomation.data.Constants.*;

public class SoapDemoTests {
    private SoapDemoSteps soapDemoSteps= new SoapDemoSteps();
    @Description("create FindPerson request and send it and validate presend of that person")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void findPersonTest(){
        FindPerson findPerson= soapDemoSteps.createRequest("10");
        String body = Marshall.marshallSoapRequest(findPerson);
        Response resp = soapDemoSteps.sendService(DEMO_URL,FIND_ACT,body);
        FindPersonResponse findPersonResponse = Unmarshall.unmarshallResponse(resp.body().asString(), FindPersonResponse.class);

        soapDemoSteps.validatePresence(findPersonResponse)
                .checkStreetElements(findPersonResponse)
                .validateName(findPersonResponse,EXP_PERSONNAME)
                .validateColor(findPersonResponse)
                .validateNumberOfCharacters(findPersonResponse)
                .validateCityState(findPersonResponse)
                .checkSSN(findPersonResponse)
                .validateZipCodes(findPersonResponse);

    }



}
