package com.bullish.mall.integration;

import com.bullish.mall.api.request.ProductDto;
import com.bullish.mall.core.product.*;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductApiTest extends TestWithUser {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        RestAssuredMockMvc.mockMvc(mockMvc);

        tagRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void fail_to_get_products_without_auth()
    {
        given()
                .contentType("application/json")
                .when()
                .get("/product")
                .then()
                .statusCode(401);
    }

    @Test
    public void success_get_products()
    {
        createMockProduct();
        createMockProduct();
        given()
                .contentType("application/json")
                .header("Authorization", "Token " + userToken)
                .when()
                .get("/product")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2))
                .body("[0].name", equalTo("name"))
                .body("[0].content", equalTo("content"))
                .body("[0].sku.price", equalTo(200.00F));
    }

    @Test
    public void fail_to_valid_product_dto()
    {
        given()
                .contentType("application/json")
                .header("Authorization", "Token " + adminToken)
                .body(ProductDto.builder()
                        .name("")
                        .price(new BigDecimal("-100.223"))
                        .content("")
                        .tags(Arrays.asList())
                        .build()
                )
                .when()
                .post("/product")
                .then()
                .statusCode(422)
                .body("errors.price", hasItem("must be greater than or equal to 0"))
                .body("errors.price", hasItem("numeric value out of bounds (<8 digits>.<2 digits> expected)"))
                .body("errors.name[0]", equalTo("can't be empty"))
                .body("errors.content[0]", equalTo("can't be empty"))
                .body("errors.tags[0]", equalTo("need at least one tag"));
    }

    @Test
    public void fail_to_create_product_for_user()
    {
        given()
                .contentType("application/json")
                .header("Authorization", "Token " + userToken)
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
                .statusCode(403);
    }
    @Test
    public void success_to_create_product_without_duplicate_tags()
    {
        given()
                .contentType("application/json")
                .header("Authorization", "Token " + adminToken)
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

    @Test
    public void fail_to_delete_invalid_product_id() {
        given()
                .contentType("application/json")
                .header("Authorization", "Token " + adminToken)
                .when()
                .delete("/product/xxxx")
                .then()
                .statusCode(400)
                .body("detail", equalTo("Failed to convert 'id' with value: 'xxxx'"));
    }

    @Test
    public void fail_to_delete_product_for_user() {
        given()
                .contentType("application/json")
                .header("Authorization", "Token " + userToken)
                .when()
                .delete("/product/1")
                .then()
                .statusCode(403);
    }

    @Test
    public void success_to_delete_product() {
        Long id = createMockProduct().getId();
        given()
                .contentType("application/json")
                .header("Authorization", "Token " + adminToken)
                .when()
                .delete("/product/" + id)
                .then()
                .statusCode(200);
        Assertions.assertEquals(productRepository.count(), 0);
    }

    private Product createMockProduct() {
        return productRepository.save(Product.builder()
                .name("name")
                .content("content")
                .Sku(Sku.builder().stocks(0L).price(new BigDecimal("200.00")).build())
                .tags(new HashSet<>(Arrays.asList(Tag.builder().name("tag").build())))
                .build()
        );
    }
}
