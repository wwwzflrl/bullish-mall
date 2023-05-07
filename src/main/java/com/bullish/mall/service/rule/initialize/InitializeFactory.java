package com.bullish.mall.service.rule.initialize;

import com.bullish.mall.entity.Discount;
import com.bullish.mall.service.rule.DiscountStrategy;
import com.bullish.mall.util.constant.ConditionEnum;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitializeFactory {
  @Autowired FullPieceInitializeStrategy fullPieceInitializeStrategy;

  @Autowired FullAmountInitializeStrategy fullAmountInitializeStrategy;

  @Autowired EveryNInitializeStrategy everyNInitializeStrategy;

  @Autowired DefaultInitializeStrategy defaultInitializeStrategy;

  public DiscountStrategy getBean(Discount discount) {
    String type =
        Optional.ofNullable(discount)
            .map(e -> e.getConfig())
            .map(e -> e.getConditionConfig())
            .map(e -> e.getType())
            .orElse("");
    ConditionEnum conditionEnum = ConditionEnum.valueOf(type);
    switch (conditionEnum) {
      case FULL_AMOUNT:
        return fullAmountInitializeStrategy;
      case FULL_PIECE:
        return fullPieceInitializeStrategy;
      case EVERY_N:
        return everyNInitializeStrategy;
      default:
        return defaultInitializeStrategy;
    }
  }
}
