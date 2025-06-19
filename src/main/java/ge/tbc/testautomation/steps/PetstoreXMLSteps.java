package ge.tbc.testautomation.steps;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ge.tbc.testautomation.data.models.pets.xml.Category;
import ge.tbc.testautomation.data.models.pets.xml.Tag;
import ge.tbc.testautomation.data.models.requests.petstore.PetRequest;
import ge.tbc.testautomation.data.models.responses.petstore.PetResponse;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

public class PetstoreXMLSteps {
    @Step("Create pet")
    public PetRequest createPet(){
        PetRequest pet = new PetRequest();
        pet.setId(6969);
        pet.setName("doggie");
        pet.setStatus("available");

        Category category = new Category();
        category.setId(0);
        category.setName("string");
        pet.setCategory(category);

        Tag tag = new Tag();
        tag.setId(0);
        tag.setName("string");
        pet.setTags(List.of(tag));

        return pet;
    }
    @Step("serialize request and post pet")
    public Response serializeAndAddPet(PetRequest pet) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        String xmlBody = xmlMapper.writeValueAsString(pet);

        return given()
                .log().all()
                .contentType(ContentType.XML)
                .accept(ContentType.XML)
                .body(xmlBody)
                .when()
                .post("https://petstore3.swagger.io/api/v3/pet");
    }
    @Step("validate response")

    public PetstoreXMLSteps validateResponse(Response response) {

        response.then().assertThat().statusCode(200);
        return this;
    }
    @Step("deserialize pet")

    public PetResponse deserializePet(Response response){

        return response.then().log().all()
                .statusCode(200)
                .extract().as(PetResponse.class);
    }
    @Step("validate pet name is : {name}")

    public PetstoreXMLSteps validatePetName(PetResponse pet, String name){
        assertThat(pet.getName(),equalTo(name));
        return this;
    }
    @Step("validate pet id is  : {expectedId}")

    public PetstoreXMLSteps validatePetId(PetResponse pet, long expectedId) {
        assertThat(pet.getId(), equalTo(expectedId));
        return this;
    }

    public PetstoreXMLSteps validatePetStatus(PetResponse pet, String expectedStatus) {
        assertThat(pet.getStatus(), equalTo(expectedStatus));
        return this;
    }


    public PetstoreXMLSteps validateTags(PetResponse pet, String... expectedTagNames) {
        var actualTagNames = pet.getTags()
                .stream()
                .map(Tag::getName)
                .toList();
        assertThat(actualTagNames, containsInAnyOrder(expectedTagNames));

        return this;
    }


}
