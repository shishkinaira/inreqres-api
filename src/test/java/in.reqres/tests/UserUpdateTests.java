package in.reqres.tests;

import in.reqres.models.UserUpdatePatchRequestModel;
import in.reqres.models.UserUpdatePatchResponseModel;
import in.reqres.models.UserUpdatePutRequestModel;
import in.reqres.models.UserUpdatePutResponseModel;
import org.junit.jupiter.api.Test;

import static in.reqres.helpers.CustomAllureListener.withCustomTemplates;
import static in.reqres.helpers.TestData.getData;
import static in.reqres.specs.UserUpdateSpec.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserUpdateTests extends TestBase {

    @Test
    void userPutUpdateTest() {
        UserUpdatePutRequestModel user = new UserUpdatePutRequestModel();
        user.setName(getData("name"));
        user.setJob(getData("job"));
        UserUpdatePutResponseModel loginRequest = step("Update user by Put", () ->
                given(userPutUpdateRequestSpec)
                        .filter(withCustomTemplates())
                        .body(user)
                        .when()
                        .put("/api/users/2")
                        .then()
                        .spec(userPutUpdateResponseSpec)
                        .extract().as(UserUpdatePutResponseModel.class));

        step("Check response name", () ->
                assertEquals("morpheus", loginRequest.getName()));

        step("Check response job", () ->
                assertEquals("zion resident", loginRequest.getJob()));
    }

    @Test
    void userPatchUpdateTest() {
        UserUpdatePatchRequestModel user = new UserUpdatePatchRequestModel();
        user.setName(getData("name"));
        user.setJob(getData("job"));
        UserUpdatePatchResponseModel loginRequest = step("Update user by Patch", () ->
                given(userPatchUpdateRequestSpec)
                        .filter(withCustomTemplates())
                        .body(user)
                        .when()
                        .patch("/api/users/2")
                        .then()
                        .spec(userPatchUpdateResponseSpec)
                        .extract().as(UserUpdatePatchResponseModel.class));

        step("Check response name", () ->
                assertEquals("morpheus", loginRequest.getName()));

        step("Check response job", () ->
                assertEquals("zion resident", loginRequest.getJob()));
    }

    @Test
    void userUpdatePutCodeTest() {
        UserUpdatePutRequestModel user = new UserUpdatePutRequestModel();
        user.setName(getData("name"));
        user.setJob(getData("job"));
        given(userPutUpdateRequestSpec)
                .filter(withCustomTemplates())
                .body(user)
                .when()
                .put("/api/users/2")
                .then()
                .spec(userPutUpdateResponseSpec);
    }

    @Test
    void userUpdatePatchCodeTest() {
        UserUpdatePutRequestModel user = new UserUpdatePutRequestModel();
        user.setName(getData("name"));
        user.setJob(getData("job"));
        given(userPatchUpdateRequestSpec)
                .filter(withCustomTemplates())
                .body(user)
                .when()
                .put("/api/users/2")
                .then()
                .spec(userPatchUpdateResponseSpec);
    }
}
