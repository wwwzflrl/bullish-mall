package com.bullish.mall.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemDto {
    private ProductDto product;

    private Long quantity;

    private DiscountDto chosenDiscount;
}