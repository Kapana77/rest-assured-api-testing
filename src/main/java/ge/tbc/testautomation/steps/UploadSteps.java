package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.data.models.pets.UploadImageResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class UploadSteps {

    public Response uploadImage(long petId, String metadata, File imageFile) {
        return given()
                .baseUri("https://petstore.swagger.io/v2")
                .contentType(ContentType.MULTIPART)
                .multiPart("additionalMetadata", metadata)
                .multiPart("file", imageFile)
                .when()
                .post("/pet/" + petId + "/uploadImage");
    }

    public UploadSteps validateMetaData(Response response, String metadata) {
        UploadImageResponse uploadResponse = response.then().statusCode(200).extract().as(UploadImageResponse.class);
        assertThat(uploadResponse.getMessage(), containsString(metadata));
        return this;
    }

    public UploadSteps validateImageName(Response response, String fileName) {
        UploadImageResponse uploadResponse = response.then().statusCode(200).extract().as(UploadImageResponse.class);
        assertThat(uploadResponse.getMessage(), containsString(fileName));
        return this;
    }

    public UploadSteps validateSize(Response response, long expectedSize) {
        UploadImageResponse uploadResponse = response.then().statusCode(200).extract().as(UploadImageResponse.class);
        assertThat(uploadResponse.getMessage(), containsString(String.valueOf(expectedSize)));
        return this;
    }
}
