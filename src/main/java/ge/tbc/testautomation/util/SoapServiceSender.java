package ge.tbc.testautomation.util;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.XmlConfig;
import io.restassured.response.Response;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class SoapServiceSender {

    public static Response send(String url, String action, String body) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SoapAction", action)
                .body(body)
                .post(url);
    }

//    public static RestAssuredConfig createConfig(String prefix,String ns, String prefix2, String ns2) { //aux method for namemspaces config creation
//        return RestAssuredConfig
//                .newConfig()
//                .xmlConfig(XmlConfig.xmlConfig()
//                        .declareNamespace(prefix, ns)
//                        .declareNamespace(prefix2, ns2)
//                );
//    }

    public static Response sendWithNameSpaces(String url,String action, String body) {

        return given()
                .filter(new AllureRestAssured())
                .config(RestAssuredConfig
                        .newConfig()
                        .xmlConfig(XmlConfig.xmlConfig()
                                .declareNamespace("SOAP-ENV", "http://schemas.xmlsoap.org/soap/envelope/")
                                .declareNamespace("ns2", "http://interfaces.soap.springboot.example.com")
                        ))
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SoapAction", action)
                .body(body)
                .when()
                .post(url);

    }
}