package com.bullish.mall.dto;

import java.util.List;

public class CartDto {
    private Long id;

    private List<CartItemDto> cartItemList;

    private DiscountDto chosenDiscount;
}
