package com.bullish.mall.integration;

import com.bullish.mall.api.request.LoginDto;
import com.bullish.mall.api.security.DefaultJwtService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;


import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.core.IsEqual.equalTo;
@SpringBootTest
@AutoConfigureMockMvc
public class UserApiTest extends TestWithUser{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DefaultJwtService jwtService;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void fail_to_valid_login_dto() throws Exception
    {
        given()
                .contentType("application/json")
                .body(LoginDto.builder().username("").build())
                .when()
                .post("/user/login")
                .then()
                .statusCode(422)
                .body("errors.username[0]", equalTo("can't be empty"));
    }

    @Test
    public void fail_to_login_no_valid_user() throws Exception
    {
        given()
                .contentType("application/json")
                .body(LoginDto.builder().username("fake user").build())
                .when()
                .post("/user/login")
                .then()
                .statusCode(422)
                .body("message", equalTo("Invalid User Name"));
    }

    @Test
    public void success_to_login_as_admin() throws Exception
    {
        given()
                .contentType("application/json")
                .body(LoginDto.builder().username("admin").build())
                .when()
                .post("/user/login")
                .then()
                .statusCode(200)
                .body("username", equalTo("admin"))
                .body("username", (e) -> equalTo(jwtService.getSubFromToken(e.path("token")).get()));
    }
}
