package com.bullish.mall.service.rule.condition.impl;

import com.bullish.mall.dto.result.ConditionResult;
import com.bullish.mall.entity.BasketItem;
import com.bullish.mall.service.rule.condition.ConditionStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FullAmountConditionStrategyImpl implements ConditionStrategy {
  @Override
  public ConditionResult validate(BasketItem BasketItem) {
    BigDecimal quantity = new BigDecimal(BasketItem.getQuantity());
    BigDecimal price = BasketItem.getSku().getPrice();
    BigDecimal totalAmount = quantity.multiply(price);
    BigDecimal expectedAmount =
        BasketItem.getDiscount().getConfig().getConditionConfig().getAmount();
    Boolean valid = totalAmount.compareTo(expectedAmount) > 0;
    return ConditionResult.builder().valid(valid).count(1).build();
  }
}
