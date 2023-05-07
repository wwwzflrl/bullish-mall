package com.bullish.mall.dto;

import java.util.List;

public class OrderDto {
  private Long id;

  private List<BasketItemDto> cartItemList;

  private DiscountDto chosenDiscount;
}
