package tests;

import ge.tbc.testautomation.steps.PetStoreSteps;
import ge.tbc.testautomation.steps.UploadSteps;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class UploadTest {

    UploadSteps uploadSteps = new UploadSteps();

    long petId = 3;
    String additionalMetadata = "pet picture";
    String filePath = "src/main/resources/pet.jpg";
    File imageFile = new File(filePath);

    @Test
    public void uploadPetImage() {
        long expectedSize = imageFile.length();
        String fileName = imageFile.getName();

        Response response = uploadSteps.uploadImage(petId, additionalMetadata, imageFile);
        uploadSteps.validateMetaData(response,additionalMetadata)
                .validateImageName(response,fileName)
                .validateSize(response,expectedSize);
    }
}


