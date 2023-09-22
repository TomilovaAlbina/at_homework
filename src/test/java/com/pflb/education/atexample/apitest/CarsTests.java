package com.pflb.education.atexample.apitest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pflb.education.atexample.dto.Car;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


import java.io.IOException;
import java.io.InputStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CarsTests extends BaseApiTest{
    @Test
    @Description("Создать автомобиль. Валидные данные")
    public void createCarValidTest() throws IOException {
        InputStream testData = BaseApiTest.class.getClassLoader().getResourceAsStream("carsTestData.json");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(testData)
                .when()
                .post("/car");

        response.then()
                .statusCode(201)
                .body(notNullValue());

        InputStream testData2 = BaseApiTest.class.getClassLoader().getResourceAsStream("carsTestData.json");
        Car responseCar = response.getBody().as(Car.class);
        Car requestCar = new ObjectMapper().readValue(testData2, Car.class);

        requestCar.id = responseCar.id;

        Assertions.assertEquals(requestCar, responseCar);
    }


    @ParameterizedTest
    @CsvFileSource(resources =  "/carsIdsToDelete.csv", numLinesToSkip = 1)
    @Description("Удалить автомобиль с id")
    public void deleteCarTest(Integer id) throws IOException {

        Response response = given()
                .contentType(ContentType.JSON)
                .header("accept", "*/*")
                .when()
                .delete("/car/" + id);

        Assertions.assertEquals(204, response.statusCode());
    }

    @Test
    @Description("Тест создание авто с невалидными данными(некорректный engineType , в ответе код 400 Bad Request")
    public void createCarNegative_Test() throws IOException {

        InputStream testData = BaseApiTest.class.getClassLoader().getResourceAsStream("carsTestData2.json");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(testData)
                .when()
                .post("/car");

        response.then()
                .statusCode(400);
    }
}
