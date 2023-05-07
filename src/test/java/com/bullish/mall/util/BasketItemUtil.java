package com.bullish.mall.util;

import com.bullish.mall.dto.DiscountConfigDto;
import com.bullish.mall.entity.BasketItem;
import com.bullish.mall.entity.Discount;
import com.bullish.mall.entity.Product;
import com.bullish.mall.entity.Sku;

import java.math.BigDecimal;

public class BasketItemUtil {

  public static BasketItem createMockData(
      Long quantity,
      String type,
      BigDecimal paramX,
      BigDecimal paramY,
      Boolean deleted,
      BigDecimal price) {
    return BasketItem.builder()
        .quantity(quantity)
        .discount(
            Discount.builder()
                .config(
                    DiscountConfigDto.builder().type(type).paramX(paramX).paramY(paramY).build())
                .build())
        .product(Product.builder().deleted(deleted).build())
        .sku(Sku.builder().price(price).build())
        .build();
  }
}
