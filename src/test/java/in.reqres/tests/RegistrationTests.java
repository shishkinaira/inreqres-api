package in.reqres.tests;

import in.reqres.models.CredentialsModel;
import in.reqres.models.RegistrationRequestUnsuccessfulModel;
import in.reqres.models.RegistrationResponseSuccessfulModel;
import in.reqres.models.RegistrationResponseUnsuccessfulModel;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import static in.reqres.helpers.CustomAllureListener.withCustomTemplates;
import static in.reqres.helpers.TestData.getData;
import static in.reqres.specs.RegistrationSpec.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationTests extends TestBase {
    @Test
    void successfulRegistrationTest() {
        CredentialsModel credentials = new CredentialsModel(getData("email"), getData("password"));
        RegistrationResponseSuccessfulModel registrationResponse = step("Make request", () ->
                given(successRegistrationRequestSpec)
                        .body(credentials)
                        .when()
                        .post("/api/register")
                        .then()
                        .spec(successRegistrationResponseSpec)
                        .extract().as(RegistrationResponseSuccessfulModel.class));

        step("Check response token", () ->
                assertEquals("QpwL5tke4Pnpja7X4", registrationResponse.getToken()));
    }

    @Test
    void unSuccessfulRegistrationTest() {
        RegistrationRequestUnsuccessfulModel requestEmail = new RegistrationRequestUnsuccessfulModel();
        requestEmail.setEmail("sydney@fife");
        RegistrationResponseUnsuccessfulModel unsuccessRegistrationResponse = step("Make request", () ->
                given(unsuccessRegistrationRequestSpec)
                        .body(requestEmail)
                        .when()
                        .post("/api/register")
                        .then()
                        .spec(unsuccessRegistrationResponseSpec)
                        .extract().as(RegistrationResponseUnsuccessfulModel.class));

        step("Check response error text", () ->
                assertEquals("Missing password", unsuccessRegistrationResponse.getError()));
    }

}

