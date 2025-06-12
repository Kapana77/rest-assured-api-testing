package tests;

import com.github.javafaker.Faker;
import ge.tbc.testautomation.data.PetRequests;
import ge.tbc.testautomation.steps.PetStoreSteps;
import ge.tbc.testautomation.util.RetryAnalyzer;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class MorePetStoreTests {

    Faker faker = new Faker();
    long petId;
    String petName;
    String status = "available";


    PetRequests requests = new PetRequests();
    PetStoreSteps steps = new PetStoreSteps();

    @Test(priority = 1)
    public void testAddPet() {
        petId = faker.number().randomNumber();
        petName = faker.name().firstName();

        JSONObject petPayload = requests.buildPetPayload(petId, petName, status);
        Response response = requests.addPet(petPayload);

        steps.validatePetCreated(response, petId, petName, status);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class)
    public void testFindPetByStatus() {
        Response response = requests.findPetsByStatus(status);
        steps.validatePetExistsInResponse(response, petId);

        JSONObject petResponse = steps.findPetInListById(response, petId);
        steps.validatePetName(petResponse, petName)
                .validatePetStatus(petResponse, status);
    }


    @Test(priority = 3,retryAnalyzer = RetryAnalyzer.class)
    public void testUpdatePet() {
        String newName = faker.name().firstName();
        String newStatus = "sold";

        Response updateResponse = requests.updatePetWithForm(petId, newName, newStatus);
        steps.validateUpdateSuccess(updateResponse);

        Response getResponse = requests.getPetById(petId);

        steps.validatePetUpdatedName(getResponse, newName)
                .validatePetUpdatedStatus(getResponse, newStatus);

    }
}
