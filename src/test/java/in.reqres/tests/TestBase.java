package in.reqres.tests;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;


public class TestBase {
    @BeforeAll
    static void setup() {

        Configuration.baseUrl = System.getProperty("baseUrl", "https://reqres.in/");
        //RestAssured.baseURI = "https://reqres.in/";
    }
}
