package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import ge.tbc.testautomation.data.models.requests.petstore.PetRequest;
import ge.tbc.testautomation.data.models.responses.petstore.PetResponse;
import ge.tbc.testautomation.steps.PetstoreXMLSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.annotations.Test;
@Feature("Pet Store API")
public class PetstoreXMLTest extends BaseTest {
    private PetstoreXMLSteps petstoreXMLSteps = new PetstoreXMLSteps();

    @Description("add pet store with xml body")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void addPetToPetstoreXMLTest() throws JsonProcessingException {
        PetRequest petRequestBody = petstoreXMLSteps.createPet();

        Response respone = petstoreXMLSteps.serializeAndAddPet(petRequestBody);


        PetResponse addedPet = petstoreXMLSteps.validateResponse(respone)
                .deserializePet(respone);


        petstoreXMLSteps.validatePetName(addedPet,"doggie")
                .validatePetId(addedPet,6969)
                .validateTags(addedPet,"string");


    }

}
