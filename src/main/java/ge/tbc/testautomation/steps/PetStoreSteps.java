package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.data.Constants;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class PetStoreSteps {
    public void validatePetCreated(Response response, long expectedId, String expectedName, String expectedStatus) {
        response.then().statusCode(200)
                .body("id", equalTo((int) expectedId))
                .body("name", equalTo(expectedName))
                .body("status", equalTo(expectedStatus));
    }

    public JSONObject findPetInListById(Response response, long petId) {
        List<Object> pets = response.jsonPath().getList("$");
        for (Object obj : pets) {
            JSONObject pet = new JSONObject((java.util.Map<?, ?>) obj);
            if (pet.getLong("id") == petId) {
                return pet;
            }
        }
        throw new AssertionError(Constants.NO_PET_ERROR);
    }

    public void validatePetExistsInResponse(Response response, long petId) {
        List<Integer> ids = response.jsonPath().getList("id");
        assertThat(ids, hasItem((int) petId));
    }



    public PetStoreSteps validatePetName(JSONObject pet, String expectedName) {
        assertThat(pet.getString("name"), equalTo(expectedName));
        return this;
    }
    public PetStoreSteps validatePetStatus(JSONObject pet, String expectedStatus) {
        assertThat(pet.getString("status"), equalTo(expectedStatus));
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

    public PetStoreSteps validatePetUpdatedStatus(Response response,String expectedStatus) {
        response.then().statusCode(200)
                .body("status", equalTo(expectedStatus));
        return this;
    }
}
