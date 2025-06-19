package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.data.models.requests.petstore.OrderRequest;
import ge.tbc.testautomation.data.models.responses.petstore.OrderResponse;
import io.restassured.http.ContentType;

import java.time.OffsetDateTime;

import static io.restassured.RestAssured.given;

public class PetstoreLombokSteps {

    public OrderRequest createOrderRequest() {
        return new OrderRequest()
                .setId(99999999L)
                .setPetId(198772L)
                .setQuantity(7)
                .setShipDate(OffsetDateTime.parse("2025-06-16T20:55:12.036Z"))
                .setStatus("approved")
                .setComplete(true);
    }
    public OrderResponse postAndGetResponse(OrderRequest orderRequest) {
        return given().log().all()
                .baseUri("https://petstore3.swagger.io/api/v3")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(orderRequest)
                .when()
                .post("/store/order")
                .then()
                .statusCode(200)
                .extract()
                .body().as(OrderResponse.class);
    }
}
