package in.reqres.tests;

import in.reqres.models.CredentialsModel;
import in.reqres.models.LoginRequestUnsuccessfulModel;
import in.reqres.models.LoginResponseSuccessfulModel;
import in.reqres.models.LoginResponseUnsuccessfulModel;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import static in.reqres.helpers.CustomAllureListener.withCustomTemplates;
import static in.reqres.helpers.TestData.getData;
import static in.reqres.specs.LoginSpec.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTests extends TestBase {
    @Test
    void successfulLoginTest() {
        CredentialsModel credentials = new CredentialsModel(getData("email"), getData("password"));
        LoginResponseSuccessfulModel loginRequest = step("Login by existing user", () ->
                given(LoginRequestSpec)
                        .filter(withCustomTemplates())
                        .body(credentials)
                        .when()
                        .post("/api/login")
                        .then()
                        .spec(LoginResponseSpec)
                        .extract().as(LoginResponseSuccessfulModel.class));

        step("Check response token", () ->
                assertEquals("QpwL5tke4Pnpja7X4", loginRequest.getToken()));
    }

    @Test
    void unsuccessfulLoginTest() {
        LoginRequestUnsuccessfulModel requestEmail = new LoginRequestUnsuccessfulModel();
        requestEmail.setEmail(getData("email400"));
        LoginResponseUnsuccessfulModel unsuccessfullLoginResponse = step("Login without password", () ->
                given(LoginRequestSpec)
                        .filter(withCustomTemplates())
                        .contentType(JSON)
                        .body(requestEmail)
                        .when()
                        .post("/api/login")
                        .then()
                        .spec(LoginResponseSpec)
                        .extract().as(LoginResponseUnsuccessfulModel.class));

        step("Check response error text", () ->
                assertEquals("Missing password", unsuccessfullLoginResponse.getError()));
    }

    @Test
    void unsuccessfulLoginCodeTest() {
        LoginRequestUnsuccessfulModel requestEmail = new LoginRequestUnsuccessfulModel();
        requestEmail.setEmail(getData("email400"));
        ValidatableResponse unsuccessfullLoginResponse = step("Login without password. Code checking", () ->
                given(LoginRequestSpec)
                        .filter(withCustomTemplates())
                        .contentType(JSON)
                        .body(requestEmail)
                        .when()
                        .post("/api/login")
                        .then()
                        .statusCode(400));
    }
}
