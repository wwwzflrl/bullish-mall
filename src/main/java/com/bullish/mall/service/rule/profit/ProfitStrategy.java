package com.bullish.mall.service.rule.profit;

import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.entity.BasketItem;

public interface ProfitStrategy {
  DiscountResult calculate(BasketItem BasketItem, DiscountResult discountResult);
}
