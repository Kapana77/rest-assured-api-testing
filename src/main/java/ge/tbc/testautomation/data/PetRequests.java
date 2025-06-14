package ge.tbc.testautomation.data;

import ge.tbc.testautomation.data.models.pets.Category;
import ge.tbc.testautomation.data.models.pets.Pet;
import ge.tbc.testautomation.data.models.pets.Status;
import ge.tbc.testautomation.data.models.pets.Tag;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class PetRequests {

    private static final String BASE_URI = Constants.PETSTORE_BASEURI;

    public Pet buildPetPayload(long id, String name, Status status) {
        Category category = new Category(99, Constants.CATEGORY);
        List<Tag> tags = List.of(new Tag(8, "friendly"));
        List<String> photoUrls = List.of("url1", "url2", "url3");

        return new Pet(id, category, name, photoUrls, tags, status);
    }

    public Response addPet(Pet pet) {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post("/pet");
    }

    public Response findPetsByStatus(Status status) {
        return given()
                .baseUri(BASE_URI)
                .queryParam("status", status.getValue())
                .when()
                .get("/pet/findByStatus");
    }

    public Response updatePetWithForm(long petId, String newName, String newStatus) {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.URLENC)
                .formParam("name", newName)
                .formParam("status", newStatus)
                .when()
                .post("/pet/" + petId);
    }

    public Response getPetById(long petId) {
        return given()
                .baseUri(BASE_URI)
                .when()
                .get("/pet/" + petId);
    }

}
