package com.bullish.mall.service.rule;

import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.entity.BasketItem;

public interface DiscountStrategy {
  DiscountResult handle(BasketItem BasketItem, DiscountResult discountResult);
}
