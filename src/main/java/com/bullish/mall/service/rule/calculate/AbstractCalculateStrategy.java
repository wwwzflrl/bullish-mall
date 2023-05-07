package com.bullish.mall.service.rule.calculate;

import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.entity.BasketItem;
import com.bullish.mall.service.rule.DiscountStrategy;

abstract class AbstractCalculateStrategy implements DiscountStrategy {

  @Override
  public DiscountResult handle(BasketItem basketItem, DiscountResult discountResult) {
    this.calculateOtherFields(basketItem, discountResult);
    discountResult.calculateMetrics();
    return discountResult;
  }

  protected abstract void calculateOtherFields(
      BasketItem basketItem, DiscountResult discountResult);
}
