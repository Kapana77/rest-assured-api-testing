package ge.tbc.testautomation.util;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

public class AllureRestAssuredFilterSetup {
    public static void setup() {
        RestAssured.filters(new AllureRestAssured());
    }

}
