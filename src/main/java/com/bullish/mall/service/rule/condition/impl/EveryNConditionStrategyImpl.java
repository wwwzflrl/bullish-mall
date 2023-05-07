package com.bullish.mall.service.rule.condition.impl;

import com.bullish.mall.dto.result.ConditionResult;
import com.bullish.mall.entity.BasketItem;
import com.bullish.mall.service.rule.condition.ConditionStrategy;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class EveryNConditionStrategyImpl implements ConditionStrategy {
  @Override
  public ConditionResult validate(BasketItem BasketItem) {
    BigDecimal quantity = new BigDecimal(BasketItem.getQuantity());
    BigDecimal amount = BasketItem.getDiscount().getConfig().getConditionConfig().getAmount();
    Integer count = quantity.divide(amount).intValue();
    Boolean valid = count > 0;
    return ConditionResult.builder().valid(valid).count(count).build();
  }
}
