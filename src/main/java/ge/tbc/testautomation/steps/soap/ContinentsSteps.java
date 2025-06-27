package ge.tbc.testautomation.steps.soap;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.xml.element.Node;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static ge.tbc.testautomation.data.Constants.*;
import static io.restassured.RestAssured.given;
import static io.restassured.path.xml.XmlPath.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ContinentsSteps {
    @Step("validate count of continets is : {cnt}")
    public ContinentsSteps validateCount(String XML, int cnt) {

        int n = with(XML).get("ArrayOftContinent.tContinent.sName.size()");
        assertThat(n, equalTo(cnt));
        return this;
    }

    @Step("validate snames of continents")

    public ContinentsSteps validateNamesValues(String XML) {

        List<String> snames = with(XML).get("ArrayOftContinent.tContinent.sName.list()");

        assertThat(snames, hasItems(AFRICA, ANTARCTICA, ASIA, EUROPE, OCENANIA, THE_AMERICAS));
        return this;
    }

    @Step("get soap response")
    public Response getSoapResponse() {
        return given()
                .filter(new AllureRestAssured())
                .baseUri(C_BASEURI)
                .basePath(COUNTRY_BASEPATH)
                .when()
                .get("/ListOfContinentsByName")
                .then()
                .log().ifValidationFails(LogDetail.ALL)
                .statusCode(200)
                .extract().response();
    }

    @Step("validate name of continent with code 'AN'")
    public ContinentsSteps validateCode(String XML) {

        String name = with(XML).get("ArrayOftContinent.tContinent.find {it.sCode == 'AN'}.sName");
        assertThat(name, equalTo(ANTARCTICA));
        return this;
    }

    @Step("validate last continet's sName value")
    public ContinentsSteps validateLastName(String XML) {
        String name = with(XML).get("ArrayOftContinent.tContinent[-1].sName");
        assertThat(name, equalTo(THE_AMERICAS));
        return this;
    }

    @Step("verify that sNames are unique")
    public ContinentsSteps verifyUniqueSnames(String XML) {
        List<String> sNames = with(XML).get("ArrayOftContinent.tContinent.sName.list()");
        Set<String> uniqueSnames = new HashSet<>(sNames);
        assertThat(uniqueSnames.size() == sNames.size(), is(true));
        return this;
    }

    @Step(" Validate the presence and correctness of both `sCode` and `sName` for each `tContinent`")
    public ContinentsSteps validateSnamesAndScodes(String XML) {
        List<String> sNames = with(XML).get("ArrayOftContinent.tContinent.sName.list()");
        List<String> sCodes = with(XML).get("ArrayOftContinent.tContinent.sCode.list()");
        for (int i = 0; i < sNames.size(); ++i) {
            assertThat(sCodes.get(i), not(emptyOrNullString())); //validate presence
            assertThat(sNames.get(i), not(emptyOrNullString()));
            assertThat(sNames.get(i), containsStringIgnoringCase(sCodes.get(i))); //validate correctnes
        }
        return this;
    }

    @Step("veryfy pattern of codes")
    public ContinentsSteps verifyCodePatterns(String XML) {
        Pattern pattern = Pattern.compile("^[A-Z]{2}$"); //2 uppercase lettters pattern
        List<String> sCodes = with(XML).get("ArrayOftContinent.tContinent.sCode.list()");

        assertThat(sCodes.stream().allMatch(s -> pattern.matcher(s).matches()), is(true));
        return this;
    }

    @Step("veryfy snames is sorted order")
    public ContinentsSteps validateOrder(String XML) {
        List<String> sNames = with(XML).get("ArrayOftContinent.tContinent.sName.list()");
        List<String> sortedSNames = new ArrayList<>(sNames).stream().sorted(String::compareTo).toList();

        assertThat(sNames, equalTo(sortedSNames));
        return this;
    }

    @Step("validate that all continets are present")
    public ContinentsSteps validatePresenceOfContinents(String XML) {
        List<Node> continents = with(XML).get("ArrayOftContinent.tContinent.list()");
        List<String> names = with(XML).get("ArrayOftContinent.tContinent.sName.list()");

        assertThat(continents.size(), equalTo(6));
        assertThat(names, containsInAnyOrder(AFRICA, ANTARCTICA, THE_AMERICAS, ASIA, OCENANIA, EUROPE));
        return this;
    }

    @Step("validate that names have no numerich characters")
    public ContinentsSteps validateNoNumericCharacters(String XML) {
        List<String> names = with(XML).get("ArrayOftContinent.tContinent.sName.list()");
        Pattern digitPattern = Pattern.compile(".*\\d.*");

        assertThat(names.stream().anyMatch(s -> digitPattern.matcher(s).matches()), is(false)); //assert that none of them has numeric char
        return this;
    }

    @Step("find `sCode` that starting with `O` and ensure that is `Ocenania`")
    public ContinentsSteps validateOcenania(String XML) {
        List<String> sNames = with(XML).get("ArrayOftContinent.tContinent.sName.list()");
        List<String> sCodes = with(XML).get("ArrayOftContinent.tContinent.sCode.list()");
        for (int i = 0; i < sCodes.size(); ++i) {
            if (sCodes.get(i).startsWith("O")) {
                assertThat(sNames.get(i), equalTo(OCENANIA));
                return this;
            }
        }

        throw new RuntimeException(NO_OCENANIA_ERROR);
    }

    @Step("findAll `sName` that stars with `A` and ends with `ca`")
    public ContinentsSteps startWithA(String XML) {
        List<String> sNames = with(XML).get("ArrayOftContinent.tContinent.sName.list()");

        List<String> matches = new ArrayList<>();
        for (String name : sNames) {
            if (name.startsWith("A") && name.endsWith("ca")) {
                matches.add(name);
            }
        }
        assertThat(matches, containsInAnyOrder(AFRICA, ANTARCTICA));
        return this;
    }

}




