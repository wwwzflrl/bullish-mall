package com.bullish.mall.service.rule.profit.impl;

import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.entity.BasketItem;
import java.math.BigDecimal;

import com.bullish.mall.service.rule.profit.ProfitStrategy;
import org.springframework.stereotype.Service;

@Service
public class FixAmountProfitStrategyImpl implements ProfitStrategy {
  @Override
  public DiscountResult calculate(BasketItem BasketItem, DiscountResult discountResult) {
    BigDecimal quantity = new BigDecimal(BasketItem.getQuantity());
    BigDecimal price = BasketItem.getSku().getPrice();
    BigDecimal originalAmount = quantity.multiply(price);
    BigDecimal actualAmount =
        quantity.multiply(BasketItem.getDiscount().getConfig().getProfitConfig().getUnit());
    BigDecimal discountReduce = originalAmount.subtract(actualAmount);
    return DiscountResult.builder()
        .pass(true)
        .originalAmount(originalAmount)
        .actualAmount(actualAmount)
        .discountReduce(discountReduce)
        .count(quantity.longValue())
        .build();
  }
}
