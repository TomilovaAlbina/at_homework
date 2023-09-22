package com.pflb.education.atexample.apitest;

import com.pflb.education.atexample.apitest.filters.JwtAuthFilter;
import com.pflb.education.atexample.config.ApplicationConfig;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;



public class BaseApiTest {

    protected static ApplicationConfig config;

    @BeforeAll
    public static void configInit() {
        config = new ApplicationConfig();
        RestAssured.baseURI = config.apiUrl;
        RestAssured.filters(
                new JwtAuthFilter(config.login, config.password),
                new AllureRestAssured(),
                new RequestLoggingFilter(),
                new ResponseLoggingFilter());
    }
}