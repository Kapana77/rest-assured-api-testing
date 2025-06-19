package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ge.tbc.testautomation.data.models.requests.petstore.OrderRequest;
import ge.tbc.testautomation.data.models.responses.petstore.OrderResponse;
import ge.tbc.testautomation.steps.PetstoreLombokSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.OffsetDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PetstoreLombokTest extends BaseTest {
    private PetstoreLombokSteps petstoreLombokSteps = new PetstoreLombokSteps();
    @BeforeSuite
    public void setUpDateMapper() {
        RestAssured.config = RestAssured.config()
                .objectMapperConfig(ObjectMapperConfig.objectMapperConfig()
                        .jackson2ObjectMapperFactory((cls, charset) -> JsonMapper.builder()
                                .addModule(new JavaTimeModule())
                                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                                .build()));
    }
    @Description("add new pet and deserialize response and validate fields")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void postTest(){
        OrderRequest orderRequest = petstoreLombokSteps.createOrderRequest();


        OrderResponse orderResponse = petstoreLombokSteps.postAndGetResponse(orderRequest);


        assertThat(orderResponse.getId(),notNullValue());
        assertThat(orderResponse.getStatus(), allOf(notNullValue(),instanceOf(String.class)));
        assertThat(orderResponse.getComplete(), is(true));
        assertThat(orderResponse.getQuantity(), greaterThan(0));

    }


}
