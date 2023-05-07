package com.bullish.mall.integration;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.IsEqual.equalTo;

import com.bullish.mall.dto.param.BasketItemParam;
import com.bullish.mall.entity.BasketItem;
import com.bullish.mall.entity.Discount;
import com.bullish.mall.entity.Product;
import com.bullish.mall.repository.BasketItemRepository;
import com.bullish.mall.repository.DiscountRepository;
import com.bullish.mall.repository.ProductRepository;
import com.bullish.mall.util.DiscountUtil;
import com.bullish.mall.util.ProductUtil;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest extends TestWithUser {
  @Autowired private MockMvc mockMvc;

  @Autowired private ProductRepository productRepository;

  @Autowired private DiscountRepository discountRepository;

  @Autowired private BasketItemRepository basketItemRepository;

  @Override
  @BeforeEach
  public void setUp() throws Exception {
    RestAssuredMockMvc.mockMvc(mockMvc);
    basketItemRepository.deleteAll();
    super.setUp();
    productRepository.deleteAll();
    discountRepository.deleteAll();
  }

  @Nested
  public class getCartList {
    @Test
    public void failWithoutAuth() {
      given().contentType("application/json").when().get("/cart").then().statusCode(401);
    }

    @Test
    public void successGetUserCartItemsWithRelations() {
      createThreeCartItemList();
      given()
          .contentType("application/json")
          .header("Authorization", "Token " + userToken)
          .when()
          .get("/cart")
          .then()
          .statusCode(200)
          .body("size()", equalTo(2))
          .body("[0].quantity", equalTo(60))
          .body("[0].product.content", equalTo("content0"))
          .body("[0].sku.price", equalTo(200.00F))
          .body("[0].discount.description", equalTo("description0"))
          .body("[1].quantity", equalTo(225))
          .body("[1].product.content", equalTo("content1"))
          .body("[1].sku.price", equalTo(201.00F))
          .body("[1].discount.description", equalTo("description1"));
    }

    @Test
    public void itemKeptWhenProductRemoved() {
      List<BasketItem> basketItemList = createThreeCartItemList();
      productRepository.softDeleteById(basketItemList.get(1).getProduct().getId());
      given()
          .contentType("application/json")
          .header("Authorization", "Token " + userToken)
          .when()
          .get("/cart")
          .then()
          .statusCode(200)
          .body("size()", equalTo(2))
          .body("[0].product.deleted", equalTo(true))
          .body("[1].product.deleted", equalTo(false));
    }
  }

  @Nested
  public class createCartList {
    @Test
    public void failWithInvalidParam() {
      List<BasketItemParam> basketItemParams =
          List.of(BasketItemParam.builder().quantities(10L).build());
      given()
          .contentType("application/json")
          .header("Authorization", "Token " + userToken)
          .body(basketItemParams)
          .when()
          .post("/cart")
          .then()
          .statusCode(422)
          .body("errors.\"list[0].skuId\"", hasItem("must not be null"));
    }

    @Test
    public void failWithNoExistingEntity() {
      List<BasketItemParam> basketItemParams =
          List.of(BasketItemParam.builder().quantities(10L).skuId(5L).productId(10L).build());
      given()
          .contentType("application/json")
          .header("Authorization", "Token " + userToken)
          .body(basketItemParams)
          .when()
          .post("/cart")
          .then()
          .statusCode(422)
          .body("message", equalTo("No value present"));
    }

    @Test
    public void successCreateEntities() {
      List<Product> productList =
          productRepository.saveAll(
              List.of(ProductUtil.getDefaultProduct(0), ProductUtil.getDefaultProduct(1)));
      List<BasketItemParam> basketItemParams =
          List.of(
              BasketItemParam.builder()
                  .quantities(10L)
                  .skuId(productList.get(0).getSkuList().get(0).getId())
                  .productId(productList.get(0).getId())
                  .build(),
              BasketItemParam.builder()
                  .quantities(10L)
                  .skuId((productList.get(1).getSkuList().get(0).getId()))
                  .productId(productList.get(1).getId())
                  .build());
      given()
          .contentType("application/json")
          .header("Authorization", "Token " + userToken)
          .body(basketItemParams)
          .when()
          .post("/cart")
          .then()
          .statusCode(200)
          .body("size()", equalTo(2));
    }
  }

  @Nested
  public class deleteCart {
    @Test
    public void failToDeleteOthersItem() {
      List<BasketItem> basketItemList = createThreeCartItemList();
      given()
          .contentType("application/json")
          .header("Authorization", "Token " + adminToken)
          .body(List.of(basketItemList.get(0).getId(), basketItemList.get(1).getId()))
          .when()
          .delete("/cart")
          .then()
          .statusCode(403);
    }

    @Test
    public void successToDelete() {
      List<BasketItem> basketItemList = createThreeCartItemList();
      given()
          .contentType("application/json")
          .header("Authorization", "Token " + userToken)
          .body(List.of(basketItemList.get(1).getId(), basketItemList.get(2).getId()))
          .when()
          .delete("/cart")
          .then()
          .statusCode(200);
      Assertions.assertEquals(productRepository.count(), 2);
      Assertions.assertEquals(productRepository.count(), 2);
    }
  }

  @Nested
  public class PreviewCart {
    @Test
    public void failToPreview() {
      List<BasketItem> basketItemList = createThreeCartItemList();
      given()
          .contentType("application/json")
          .body(List.of(basketItemList.get(0).getId(), basketItemList.get(1).getId()))
          .when()
          .delete("/cart/preview")
          .then()
          .statusCode(401);
    }

    @Test
    public void successToPreview() {
      List<BasketItem> basketItemList = createThreeCartItemList();
      given()
          .contentType("application/json")
          .header("Authorization", "Token " + userToken)
          .body(List.of(basketItemList.get(1).getId(), basketItemList.get(2).getId()))
          .when()
          .get("/cart/preview")
          .then()
          .statusCode(200)
          .body("originalAmount", equalTo(57225.00F))
          .body("discountAmount", equalTo(3))
          .body("payAmount", equalTo(57222.00F))
          .body("quantity", equalTo(285))
          .body("discountCalculateDtoList.size()", equalTo(2));
    }
  }

  private List<BasketItem> createThreeCartItemList() {
    List<Product> productList =
        productRepository.saveAll(
            List.of(ProductUtil.getDefaultProduct(0), ProductUtil.getDefaultProduct(1)));
    List<Discount> discountList =
        discountRepository.saveAll(
            List.of(DiscountUtil.getDefaultDiscount(0), DiscountUtil.getDefaultDiscount(1)));

    List<BasketItem> basketItemList =
        List.of(
            BasketItem.builder()
                .user(admin)
                .product(productList.get(0))
                .sku(productList.get(0).getSkuList().get(0))
                .discount(discountList.get(0))
                .quantity(60L)
                .build(),
            BasketItem.builder()
                .user(user)
                .product(productList.get(0))
                .sku(productList.get(0).getSkuList().get(0))
                .discount(discountList.get(0))
                .quantity(60L)
                .build(),
            BasketItem.builder()
                .user(user)
                .product(productList.get(1))
                .sku(productList.get(1).getSkuList().get(0))
                .discount(discountList.get(1))
                .quantity(225L)
                .build());
    return basketItemRepository.saveAll(basketItemList);
  }
}
