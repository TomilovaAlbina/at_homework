package com.pflb.education.atexample.apitest.filters;

import com.pflb.education.atexample.dto.Auth.AuthRequest;
import com.pflb.education.atexample.dto.Auth.AuthResponse;
import io.restassured.filter.FilterContext;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.spi.AuthFilter;

import static io.restassured.RestAssured.given;

public class JwtAuthFilter implements AuthFilter {
  private final String username;
  private final String password;

  public JwtAuthFilter(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public Response filter(
      FilterableRequestSpecification requestSpec,
      FilterableResponseSpecification responseSpec,
      FilterContext ctx
  ) {
    AuthRequest authRequest = new AuthRequest(username, password);
    AuthResponse authResponse = given().auth().none().and().disableCsrf().and()
        .contentType(ContentType.JSON).body(authRequest)
        .when().post("/login").as(AuthResponse.class);
    String token = authResponse.accessToken;
    requestSpec.header("Authorization", "Bearer " + token);
    requestSpec.contentType(ContentType.JSON);

    return ctx.next(requestSpec, responseSpec);
  }


}

