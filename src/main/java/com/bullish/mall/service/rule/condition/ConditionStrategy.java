package com.bullish.mall.service.rule.condition;

import com.bullish.mall.entity.BasketItem;
import com.bullish.mall.dto.result.ConditionResult;

public interface ConditionStrategy {
  ConditionResult validate(BasketItem BasketItem);
}
