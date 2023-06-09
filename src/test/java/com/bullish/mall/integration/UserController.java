package com.bullish.mall.integration;

import com.bullish.mall.dto.param.LoginParam;
import com.bullish.mall.service.impl.DefaultJwtService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest
@AutoConfigureMockMvc
public class UserController extends TestWithUser {
  @Autowired private MockMvc mockMvc;

  @Autowired private DefaultJwtService jwtService;

  @Override
  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();
    RestAssuredMockMvc.mockMvc(mockMvc);
  }

  @Test
  public void successGetUserInfo() throws Exception {
    given()
        .contentType("application/json")
        .header("Authorization", "Token " + userToken)
        .when()
        .get("/user")
        .then()
        .statusCode(200)
        .body("username", equalTo("totti"))
        .body("admin", equalTo(false));
  }

  @Test
  public void failForInvalidRequest() throws Exception {
    given()
        .contentType("application/json")
        .body(LoginParam.builder().username("").build())
        .when()
        .post("/user/login")
        .then()
        .statusCode(422)
        .body("errors.username[0]", equalTo("must not be blank"));
  }

  @Test
  public void failForInvalidUser() throws Exception {
    given()
        .contentType("application/json")
        .body(LoginParam.builder().username("fake user").build())
        .when()
        .post("/user/login")
        .then()
        .statusCode(422)
        .body("message", equalTo("Invalid User Name"));
  }

  @Test
  public void successToLoginAsAdmin() throws Exception {
    given()
        .contentType("application/json")
        .body(LoginParam.builder().username("admin").build())
        .when()
        .post("/user/login")
        .then()
        .statusCode(200)
        .body("username", equalTo("admin"))
        .body("username", (e) -> equalTo(jwtService.getSubFromToken(e.path("token")).get()));
  }
}
