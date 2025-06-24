package ge.tbc.testautomation.steps.integration;

import ge.tbc.testautomation.util.SoapServiceSender;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.newproject.FindPerson;
import org.newproject.FindPersonResponse;
import org.newproject.ObjectFactory;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SoapDemoSteps {
    ObjectFactory objectFactory = new ObjectFactory();

    @Step("Create FindPerson request with id: {0}")
    public FindPerson createRequest(String id){
        FindPerson findPerson = objectFactory.createFindPerson();
        findPerson.withId(id);
        return findPerson;
    }

    @Step("Send SOAP service request to URL: {0} with action: {1}")
    public Response sendService(String url, String action, String body) {
        return SoapServiceSender.send(url, action, body)
                .then().log().all().extract().response();
    }

    @Step("Validate response presence")
    public SoapDemoSteps validatePresence(FindPersonResponse findPerson){
        assertThat(findPerson,is(notNullValue()));
        return this;
    }

    @Step("Validate person's name is {1}")
    public SoapDemoSteps validateName(FindPersonResponse findPerson,String name){
        assertThat(findPerson.getFindPersonResult().getName(),equalTo(name));
        return this;
    }

    @Step("Validate favorite color 'Red' is present")
    public SoapDemoSteps validateColor(FindPersonResponse findPerson){
        assertThat(findPerson.getFindPersonResult().getFavoriteColors().getFavoriteColorsItem(),hasItem("Red"));
        return this;
    }

    @Step("Validate SSN length is 11 characters")
    public SoapDemoSteps validateNumberOfCharacters(FindPersonResponse findPerson){
        assertThat(findPerson.getFindPersonResult().getSSN().length(),equalTo(11));
        return this;
    }

    @Step("Check street elements in home and office addresses")
    public SoapDemoSteps checkStreetElements(FindPersonResponse findPerson){
        boolean streetsPresent = Stream.of(findPerson.getFindPersonResult().getHome(),
                        findPerson.getFindPersonResult().getOffice())
                .allMatch(addr -> addr != null && addr.getStreet() != null && !addr.getStreet().isEmpty());

        assertThat(streetsPresent,is(true));
        return this;
    }

    @Step("Validate home city and state is 'Islip, FL'")
    public SoapDemoSteps validateCityState(FindPersonResponse findPerson){
        String homeCityState = findPerson.getFindPersonResult().getHome().getCity()+ ", "
                + findPerson.getFindPersonResult().getHome().getState();
        assertThat(homeCityState, equalTo("Islip, FL"));
        return this;
    }

    @Step("Validate SSN matches pattern ###-##-####")
    public SoapDemoSteps checkSSN(FindPersonResponse findPerson){
        assertThat(findPerson.getFindPersonResult().getSSN(), Matchers.matchesPattern("\\d{3}-\\d{2}-\\d{4}"));
        return this;
    }

    @Step("Validate home and office zip codes are different")
    public SoapDemoSteps validateZipCodes(FindPersonResponse findPerson){
        assertThat(findPerson.getFindPersonResult().getHome().getZip(),
                not(equalTo(findPerson.getFindPersonResult().getOffice().getZip())));
        return this;
    }

}
