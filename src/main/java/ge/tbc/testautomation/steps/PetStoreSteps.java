package ge.tbc.testautomation.steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ge.tbc.testautomation.data.models.pets.Pet;
import ge.tbc.testautomation.data.models.pets.Status;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PetStoreSteps {
    public void validatePetCreated(Response response, long expectedId, String expectedName, Status expectedStatus) {
        response.then().statusCode(200)
                .body("id", equalTo((int) expectedId))
                .body("name", equalTo(expectedName))
                .body("status", equalTo(expectedStatus.getValue()));
    }

//    public List<Pet> getPetsList(Response response) {
//        return response.jsonPath().getList("", Pet.class);
//    }
    public List<Pet> getPetsList(Response response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response.getBody().asInputStream(), new TypeReference<List<Pet>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Pet findPetInListById(Response response, long petId) {
        List<Pet> pets = getPetsList(response);
        return pets.stream()
                .filter(pet -> pet.getId() == petId) //finds pet by id
                .findFirst()
                .orElse(null);
    }

    public void validatePetExistsInResponse(Response response, long petId) {
        List<Pet> pets = getPetsList(response);
        Assert.assertTrue(pets.stream().anyMatch(p -> p.getId() == petId));
    }

    public PetStoreSteps validatePetName(Pet pet, String expectedName) {
        assertThat(pet.getName(), equalTo(expectedName));
        return this;
    }

    public PetStoreSteps validatePetStatus(Pet pet, Status expectedStatus) {
        assertThat(pet.getStatus(), equalTo(expectedStatus.getValue()));
        return this;
    }

    public PetStoreSteps validateUpdateSuccess(Response response) {
        response.then().statusCode(200);
        return this;
    }

    public PetStoreSteps validatePetUpdatedName(Response response, String expectedName) {
        response.then().statusCode(200)
                .body("name", equalTo(expectedName));
        return this;
    }

    public PetStoreSteps validatePetUpdatedStatus(Response response, String expectedStatus) {
        response.then().statusCode(200)
                .body("status", equalTo(expectedStatus));
        return this;
    }
}
