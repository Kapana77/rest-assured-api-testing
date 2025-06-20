package soap.tests;

import ge.tbc.testautomation.steps.soap.ContinentsSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

@Feature("Continents SOAP API")
public class ContinentsTest {
    private final ContinentsSteps continentsSteps = new ContinentsSteps();

    @Description("Using XmlPath and hamcrest matchers validates response fields")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testContinents() {

        String soapResponse = continentsSteps.getSoapResponse().asString();

        continentsSteps.validateCount(soapResponse, 6)
                .validateNamesValues(soapResponse)
                .validateCode(soapResponse)
                .validateLastName(soapResponse)
                .verifyUniqueSnames(soapResponse)
                .validateSnamesAndScodes(soapResponse)
                .verifyCodePatterns(soapResponse)
                .validateOrder(soapResponse)
                .validatePresenceOfContinents(soapResponse)
                .validateNoNumericCharacters(soapResponse)
                .validateOcenania(soapResponse)
                .startWithA(soapResponse);
    }
}
