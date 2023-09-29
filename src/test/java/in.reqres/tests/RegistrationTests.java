package in.reqres.tests;
import in.reqres.models.CredentialsModel;
import in.reqres.models.RegistrationRequestUnsuccessfulModel;
import in.reqres.models.RegistrationResponseSuccessfulModel;
import in.reqres.models.RegistrationResponseUnsuccessfulModel;
import org.junit.jupiter.api.Test;

import static in.reqres.helpers.CustomAllureListener.withCustomTemplates;
import static in.reqres.helpers.TestData.getProperty;
import static in.reqres.specs.RegistrationSpec.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationTests extends TestBase {
    @Test
    void successfulRegistrationTest() {
        CredentialsModel credentials = new CredentialsModel(getProperty("email"),getProperty("password"));
        RegistrationResponseSuccessfulModel registrationResponse = step("Make request", () ->
                given(sucRegistrationRequestSpec)
                        .filter(withCustomTemplates())
                        .body(credentials)
                        .when()
                        .post("/api/register")
                        .then()
                        .spec(sucRegistrationResponseSpec)
                        .extract().as(RegistrationResponseSuccessfulModel.class));

        step("Check response token", () ->
                assertEquals("QpwL5tke4Pnpja7X4", registrationResponse.getToken()));
    }

    @Test
    void unSuccessfulRegistrationTest() {
        RegistrationRequestUnsuccessfulModel requestEmail = new RegistrationRequestUnsuccessfulModel();
        requestEmail.setEmail("sydney@fife");
        RegistrationResponseUnsuccessfulModel unRegistrationResponse = step("Make request", () ->
                given(unsRegistrationRequestSpec)
                        .filter(withCustomTemplates())
                        .body(requestEmail)
                        .when()
                        .post("/api/register")
                        .then()
                        .spec(unsucRegistrationResponseSpec)
                        .extract().as(RegistrationResponseUnsuccessfulModel.class));

        step("Check response error text", () ->
                assertEquals("Missing password", unRegistrationResponse.getError()));
    }

    @Test
    void unSuccessfulRegistrationCodeTest() {
        RegistrationRequestUnsuccessfulModel requestEmail = new RegistrationRequestUnsuccessfulModel();
        step("Check response is 400", () ->
                given(unsRegistrationRequestSpec)
                        .filter(withCustomTemplates())
                        .body(getProperty("email400"))
                        .when()
                        .post("/api/register")
                        .then()
                        .spec(unsucRegistrationResponse400Spec));
    }
}

