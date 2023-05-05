package com.bullish.mall.integration;

import com.bullish.mall.api.request.ProductDto;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductApiTest extends TestWithUser {
    @Autowired
    private MockMvc mockMvc;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void fail_to_valid_product_dto() throws Exception
    {
        given()
                .contentType("application/json")
                .body(ProductDto.builder()
                        .name("")
                        .price(new BigDecimal("100.223"))
                        .content("")
                        .tags(Arrays.asList())
                        .build()
                )
                .when()
                .post("/product")
                .then()
                .statusCode(422)
                .body("errors.prices[0]", equalTo("numeric value out of bounds (<8 digits>.<2 digits> expected)"))
                .body("errors.name[0]", equalTo("can't be empty"))
                .body("errors.content[0]", equalTo("can't be empty"))
                .body("errors.tags[0]", equalTo("need at least one tag"));
    }

    @Test
    public void success_to_create_product_without_duplicate_tags() throws Exception
    {
        given()
                .contentType("application/json")
                .body(ProductDto.builder()
                        .name("test")
                        .price(new BigDecimal("100.22"))
                        .content("Product Test")
                        .tags(Arrays.asList("Test Tag 1", "Test Tag 2", "Test Tag 1"))
                        .build()
                )
                .when()
                .post("/product")
                .then()
                .statusCode(200)
                .body("name", equalTo("test"))
                .body("content", equalTo("Product Test"))
                .body("tags.size()", equalTo(2))
                .body("tags[0].name", equalTo("Test Tag 2"))
                .body("tags[1].name", equalTo("Test Tag 1"))
                .body("sku.price", equalTo(100.22F));
    }
}
