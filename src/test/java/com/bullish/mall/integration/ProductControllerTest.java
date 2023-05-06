package com.bullish.mall.integration;

import com.bullish.mall.constant.TargetEnum;
import com.bullish.mall.dto.SkuDto;
import com.bullish.mall.dto.param.ProductParam;
import com.bullish.mall.entity.Discount;
import com.bullish.mall.entity.Product;
import com.bullish.mall.repository.DiscountRepository;
import com.bullish.mall.repository.ProductRepository;
import com.bullish.mall.util.DiscountUtil;
import com.bullish.mall.util.ProductUtil;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest extends TestWithUser {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        RestAssuredMockMvc.mockMvc(mockMvc);

        productRepository.deleteAll();
        discountRepository.deleteAll();
    }

    @Nested
    public class getProductList {
        @Test
        public void failWithoutAuth()
        {
            given()
                    .contentType("application/json")
                    .when()
                    .get("/product")
                    .then()
                    .statusCode(401);
        }

        @Test
        public void successGetProductsWithRelations()
        {
            List<Product> productList = productRepository.saveAll(List.of(
                    ProductUtil.getDefaultProduct(0),
                    ProductUtil.getDefaultProduct(1)
            ));
            List<Discount> discountList = List.of(
                    DiscountUtil.getDefaultDiscount(0),
                    DiscountUtil.getDefaultDiscount(1)
            );
            discountList.get(0).getConfig().getTargetConfig().setProductIds(List.of(productList.get(1).getId()));
            discountList.get(0).getConfig().getTargetConfig().setType(TargetEnum.SPECIFIED_PRODUCT.name());
            discountRepository.saveAll(discountList);

            given()
                    .contentType("application/json")
                    .header("Authorization", "Token " + userToken)
                    .when()
                    .get("/product")
                    .then()
                    .statusCode(200)
                    .body("size()", equalTo(2))
                    .body("[0].name", equalTo("name0"))
                    .body("[0].content", equalTo("content0"))
                    .body("[0].skuList[0].price", equalTo(200.00F))
                    .body("[0].skuList[0].tags[0]", equalTo("Tag0"))
                    .body("[0].discountList.size()", equalTo(1))
                    .body("[1].discountList.size()", equalTo(2));
        }

    }

    @Nested
    public class CreateProduct {
        @Test
        public void failValidEmptyProductParam()
        {
            given()
                    .contentType("application/json")
                    .header("Authorization", "Token " + adminToken)
                    .body(ProductParam.builder().build())
                    .when()
                    .post("/product")
                    .then()
                    .statusCode(422)
                    .body("errors.skuList", hasItem("need at least one sku"))
                    .body("errors.name", hasItem("must not be blank"))
                    .body("errors.content", hasItem("must not be blank"));
        }

        @Test
        public void failValidOtherProductParam()
        {
            given()
                    .contentType("application/json")
                    .header("Authorization", "Token " + adminToken)
                    .body(ProductParam.builder().skuList(List.of(
                            SkuDto.builder().tags(List.of("")).price(new BigDecimal(-234.55)).build()
                    )).build())
                    .when()
                    .post("/product")
                    .then()
                    .statusCode(422)
                    .body("errors.\"skuList[0].price\"", hasItems(
                            "must be greater than or equal to 0",
                            "numeric value out of bounds (<8 digits>.<2 digits> expected)"
                    ))
                    .body("errors.\"skuList[0].tags[0]\"", hasItem("must not be blank"));
        }

        @Test
        public void failCreateProductForUser()
        {
            given()
                    .contentType("application/json")
                    .header("Authorization", "Token " + userToken)
                    .body(ProductUtil.getDefaultProductParam(0))
                    .when()
                    .post("/product")
                    .then()
                    .statusCode(403);
        }

        @Test
        public void successCreateProductWithRelation()
        {
            ProductParam productParam = ProductUtil.getDefaultProductParam(0);
            productParam.getSkuList().get(0).setTags(List.of("Duplicate", "Duplicate"));
            given()
                    .contentType("application/json")
                    .header("Authorization", "Token " + adminToken)
                    .body(productParam)
                    .when()
                    .post("/product")
                    .then()
                    .body("name", equalTo("name0"))
                    .body("content", equalTo("content0"))
                    .body("skuList.size()", equalTo(1))
                    .body("skuList[0].price", equalTo(200))
                    .body("skuList[0].tags.size()", equalTo(1));
        }
    }

    @Nested
    public class deleteProduct {
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
        Long id = productRepository.save(ProductUtil.getDefaultProduct(0)).getId();
        given()
                .contentType("application/json")
                .header("Authorization", "Token " + adminToken)
                .when()
                .delete("/product/" + id)
                .then()
                .statusCode(200);
        Assertions.assertEquals(productRepository.count(), 0);
    }

    }
}
