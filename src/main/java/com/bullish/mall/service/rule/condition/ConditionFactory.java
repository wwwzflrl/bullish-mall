package com.bullish.mall.service.rule.condition;

import com.bullish.mall.service.rule.condition.impl.EveryNConditionStrategyImpl;
import com.bullish.mall.service.rule.condition.impl.FullAmountConditionStrategyImpl;
import com.bullish.mall.service.rule.condition.impl.FullPieceConditionStrategyImpl;
import com.bullish.mall.util.constant.ConditionEnum;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConditionFactory {
  @Autowired FullAmountConditionStrategyImpl fullAmountConditionStrategyImpl;

  @Autowired FullPieceConditionStrategyImpl fullPieceConditionStrategyImpl;

  @Autowired EveryNConditionStrategyImpl everyNConditionStrategyImpl;

  public Optional<ConditionStrategy> getBean(String type) {
    ConditionEnum conditionEnum = ConditionEnum.valueOf(type);
    switch (conditionEnum) {
      case FULL_AMOUNT:
        return Optional.of(fullAmountConditionStrategyImpl);
      case FULL_PIECE:
        return Optional.of(fullPieceConditionStrategyImpl);
      case EVERY_N:
        return Optional.of(everyNConditionStrategyImpl);
      default:
        return Optional.empty();
    }
  }
}
