package com.bullish.mall.service.rule.condition.impl;

import com.bullish.mall.entity.BasketItem;
import com.bullish.mall.dto.result.ConditionResult;
import com.bullish.mall.service.rule.condition.ConditionStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FullPieceConditionStrategyImpl implements ConditionStrategy {
  @Override
  public ConditionResult validate(BasketItem BasketItem) {
    BigDecimal quantity = new BigDecimal(BasketItem.getQuantity());
    BigDecimal piece = BasketItem.getDiscount().getConfig().getConditionConfig().getAmount();
    Boolean valid = quantity.compareTo(piece) > 0;
    return ConditionResult.builder().valid(valid).count(1).build();
  }
}
