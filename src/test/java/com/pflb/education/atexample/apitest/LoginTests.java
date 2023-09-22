package com.pflb.education.atexample.apitest;

import com.pflb.education.atexample.dto.Auth;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTests extends BaseApiTest {

    @Test
    @Description("Отправка POST запроса на логин с корректными данными")
    public void positiveLogin_Test(){

        Response loginResponse = loginWith(config.login, config.password);

        loginResponse.then()
                .assertThat()
                .statusCode(202)
                .body(notNullValue())
                .body(containsString("access_token"));

        Auth.AuthResponse authResponse = loginResponse.getBody().as(Auth.AuthResponse.class);

        sendTokenToReport(authResponse.accessToken);
    }

    @Test
    @Description("Негативный сценарий, логин под несуществующим пользователем")
    public void negativeLogin_Test(){
        Response loginResponse = loginWith("notuser", config.password);

        loginResponse.then()
                .assertThat()
                .statusCode(403);
    }

    @Step("Логин в систему с username = {username}, password = {password}")
    public Response loginWith(String username, String password) {
        Auth.AuthRequest authRequest = new Auth.AuthRequest(username, password);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(authRequest)
                .when()
                .post( "/login");

        return response;
    }

    @Attachment(value = "Bearer token:", type = "text/html")
    public String sendTokenToReport(String message) {
        return message;
    }

}