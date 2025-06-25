package openapi.tests;

import ge.tbc.testautomation.steps.openapi.PetstoreSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
@Feature("petstore api")
public class StoreOrderTest {

    private final PetstoreSteps steps = new PetstoreSteps();

    @BeforeSuite
    public void setUp() {
        steps.createApi();
    }
    @Description("placed order and validate it was placed")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testPlaceOrder() {
        steps
                .createOrderPayload()
                .sendPlaceOrderRequest()
                .validatePlacedOrder();
    }
    @Description("post new pet and validate")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testPostNewPet() {
        steps.postNewPet()
                .validatePetCreation();
    }

}
