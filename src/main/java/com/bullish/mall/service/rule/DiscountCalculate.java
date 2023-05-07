package com.bullish.mall.service.rule;

import com.bullish.mall.dto.DiscountCalculateDto;
import com.bullish.mall.entity.BasketItem;

public interface DiscountCalculate {
  DiscountCalculateDto handle(BasketItem BasketItem);
}
