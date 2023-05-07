package com.bullish.mall.service.rule.profit.impl;

import com.bullish.mall.dto.result.ConditionResult;
import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.entity.BasketItem;
import com.bullish.mall.service.rule.condition.ConditionStrategy;
import java.math.BigDecimal;
import java.util.List;

import com.bullish.mall.service.rule.profit.ProfitStrategy;
import org.springframework.stereotype.Service;

@Service
public class ReduceAmountProfitStrategyImpl implements ProfitStrategy {
  @Override
  public DiscountResult calculate(BasketItem BasketItem, DiscountResult discountResult) {
    BigDecimal quantity = new BigDecimal(BasketItem.getQuantity());
    BigDecimal price = BasketItem.getSku().getPrice();
    BigDecimal originalAmount = quantity.multiply(price);
    BigDecimal discountReduce = BasketItem.getDiscount().getConfig().getProfitConfig().getUnit();
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
