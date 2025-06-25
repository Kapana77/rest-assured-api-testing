package ge.tbc.testautomation.steps.openapi;

import ge.tbc.testautomation.data.Constants;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import pet.store.v3.api.PetApi;
import pet.store.v3.invoker.ApiClient;
import pet.store.v3.invoker.JacksonObjectMapper;
import pet.store.v3.model.Order;
import pet.store.v3.model.Pet;

import java.time.OffsetDateTime;
import java.util.Collections;

import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static io.restassured.config.RestAssuredConfig.config;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static pet.store.v3.invoker.ResponseSpecBuilders.shouldBeCode;
import static pet.store.v3.invoker.ResponseSpecBuilders.validatedWith;

public class PetstoreSteps {

    private Order placedOrder;
    private Response petCreationResponse;

    private ApiClient api;


    public PetstoreSteps createApi() {
        api = ApiClient.api(ApiClient.Config.apiConfig()
                .reqSpecSupplier(() -> new RequestSpecBuilder()
                        .log(LogDetail.ALL)
                        .setConfig(config()
                                .objectMapperConfig(objectMapperConfig()
                                        .defaultObjectMapper(JacksonObjectMapper.jackson())))
                        .addFilter(new ErrorLoggingFilter())
                        .setBaseUri("https://petstore3.swagger.io/api/v3")));

        return this;
    }



    @Step("Create order payload")
    public PetstoreSteps createOrderPayload() {
        placedOrder = new Order()
                .id(10L)
                .petId(1L)
                .quantity(7)
                .shipDate(OffsetDateTime.now())
                .status(Order.StatusEnum.APPROVED)
                .complete(true);
        return this;
    }

    @Step("Send POST /store/order request")
    public PetstoreSteps sendPlaceOrderRequest() {
        placedOrder = api.store().placeOrder()
                .body(placedOrder)
                .reqSpec(req -> req.log(LogDetail.ALL))
                .respSpec(resp -> resp
                        .log(LogDetail.ALL)
                        .expectStatusCode(200)
                        .expectContentType("application/json"))
                .executeAs(validatedWith(shouldBeCode(SC_OK)));
        return this;
    }

    @Step("Validate order response data")
    public PetstoreSteps validatePlacedOrder() {
        assertThat(placedOrder.getId()).isEqualTo(10L);
        assertThat(placedOrder.getPetId()).isEqualTo(1L);
        assertThat(placedOrder.getQuantity()).isEqualTo(7);
        assertThat(placedOrder.getStatus()).isEqualTo(Order.StatusEnum.APPROVED);
        assertThat(placedOrder.getComplete()).isTrue();
        return this;
    }

    @Step("Create and send POST /pet request")
    public PetstoreSteps postNewPet() {
        Pet pet = new Pet();
        pet.setName("Doggo");
        pet.setPhotoUrls(Collections.singletonList("http://example.com/photo1.jpg"));
        pet.setStatus(Pet.StatusEnum.AVAILABLE);

        PetApi petApi = PetApi.pet(() -> new RequestSpecBuilder()
                .setBaseUri("https://petstore3.swagger.io/api/v3")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        );

        petCreationResponse = petApi.addPet()
                .body(pet)
                .respSpec(respSpec -> respSpec
                        .expectStatusCode(200)
                        .expectContentType(ContentType.JSON)
                        .expectBody("name", Matchers.equalTo(Constants.EXP_PETNAME))
                        .expectBody("status", Matchers.equalTo("available")))
                .execute(resp -> resp);

        return this;
    }

    @Step("Validate pet creation response")
    public PetstoreSteps validatePetCreation() {
        petCreationResponse.then().assertThat()
                .statusCode(200)
                .body("name", Matchers.equalTo(Constants.EXP_PETNAME))
                .body("status", Matchers.equalTo("available"));

        return this;
    }
}
