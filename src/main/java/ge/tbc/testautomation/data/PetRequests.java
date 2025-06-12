package ge.tbc.testautomation.data;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class PetRequests {

    private static final String BASE_URI = "https://petstore.swagger.io/v2";

    public JSONObject buildPetPayload(long id, String name, String status) {
        JSONObject payload = new JSONObject();
        payload.put("id", id);
        payload.put("name", name);
        payload.put("status", status);
        payload.put("photoUrls", new JSONArray().put("url"));
        return payload;
    }

    public Response addPet(JSONObject petPayload) {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(petPayload.toString())
                .when()
                .post("/pet");
    }

    public Response findPetsByStatus(String status) {
        return given()
                .baseUri(BASE_URI)
                .queryParam("status", status)
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
