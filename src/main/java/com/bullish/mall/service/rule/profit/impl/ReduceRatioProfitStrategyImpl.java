package com.bullish.mall.service.rule.profit.impl;

import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.entity.BasketItem;
import com.bullish.mall.service.rule.profit.ProfitStrategy;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class ReduceRatioProfitStrategyImpl implements ProfitStrategy {
  @Override
  public DiscountResult calculate(BasketItem BasketItem, DiscountResult discountResult) {
    BigDecimal quantity = new BigDecimal(BasketItem.getQuantity());
    BigDecimal price = BasketItem.getSku().getPrice();
    BigDecimal originalAmount = quantity.multiply(price);
    BigDecimal discountReduce =
        originalAmount.multiply(BasketItem.getDiscount().getConfig().getProfitConfig().getUnit());
    BigDecimal actualAmount = originalAmount.subtract(discountReduce);
    return DiscountResult.builder()
        .pass(true)
        .originalAmount(originalAmount)
        .actualAmount(actualAmount)
        .discountReduce(discountReduce)
        .count(quantity.longValue())
        .build();
  }
}
