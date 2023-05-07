package com.bullish.mall.service.rule.profit;

import com.bullish.mall.service.rule.profit.impl.FixAmountProfitStrategyImpl;
import com.bullish.mall.service.rule.profit.impl.ReduceRatioProfitStrategyImpl;
import com.bullish.mall.service.rule.profit.impl.ReduceAmountProfitStrategyImpl;

import java.util.Optional;

import com.bullish.mall.util.constant.ProfitEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfitFactory {
  @Autowired FixAmountProfitStrategyImpl fixAmountProfitStrategyImpl;

  @Autowired ReduceRatioProfitStrategyImpl reduceRatioProfitStrategyImpl;

  @Autowired ReduceAmountProfitStrategyImpl reduceAmountProfitStrategyImpl;

  public Optional<ProfitStrategy> getBean(String type) {
    ProfitEnum profitEnum = ProfitEnum.valueOf(type);
    switch (profitEnum) {
      case FIX_AMOUNT:
        return Optional.of(fixAmountProfitStrategyImpl);
      case REDUCED_RATE:
        return Optional.of(reduceRatioProfitStrategyImpl);
      case REDUCED_AMOUNT:
        return Optional.of(reduceAmountProfitStrategyImpl);
      default:
        return Optional.empty();
    }
  }
}
