package in.reqres.tests;

import in.reqres.models.CredentialsModel;
import in.reqres.models.LoginRequestUnsuccessfulModel;
import in.reqres.models.LoginResponseSuccessfulModel;
import in.reqres.models.LoginResponseUnsuccessfulModel;
import org.junit.jupiter.api.Test;

import static in.reqres.helpers.CustomAllureListener.withCustomTemplates;
import static in.reqres.helpers.TestData.getProperty;
import static in.reqres.specs.LoginSpec.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTests extends TestBase {
    @Test
    void successfulLoginTest() {
        CredentialsModel credentials = new CredentialsModel(getProperty("email"),getProperty("password"));
        LoginResponseSuccessfulModel loginRequest = step("Login by existing user", () ->
                given(successLoginRequestSpec)
                        .filter(withCustomTemplates())
                        .body(credentials)
                        .when()
                        .post("/api/login")
                        .then()
                        .spec(successLoginResponseSpec)
                        .extract().as(LoginResponseSuccessfulModel.class));

        step("Check response token", () ->
                assertEquals("QpwL5tke4Pnpja7X4", loginRequest.getToken()));
    }

    @Test
    void unSuccessfulLoginTest() {
        LoginRequestUnsuccessfulModel requestEmail = new LoginRequestUnsuccessfulModel();
        requestEmail.setEmail(getProperty("email400"));
        LoginResponseUnsuccessfulModel unLoginResponse = step("Login without password", () ->
                given(unsuccessLoginRequestSpec)
                        .filter(withCustomTemplates())
                        .contentType(JSON)
                        .body(requestEmail)
                        .when()
                        .post("/api/login")
                        .then()
                        .spec(unsuccessLoginResponseSpec)
                        .extract().as(LoginResponseUnsuccessfulModel.class));

        step("Check response error text", () ->
                assertEquals("Missing password", unLoginResponse.getError()));
    }

    @Test
    void unSuccessfulLoginCodeTest() {
        LoginRequestUnsuccessfulModel requestEmail = new LoginRequestUnsuccessfulModel();
        requestEmail.setEmail(getProperty("email400"));
        step("Login without password. Code checking", () ->
                given(unsuccessLoginRequestSpec)
                        .filter(withCustomTemplates())
                        .contentType(JSON)
                        .body(requestEmail)
                        .when()
                        .post("/api/login")
                        .then()
                        .spec(unsuccessLoginResponseSpec));
    }
}
