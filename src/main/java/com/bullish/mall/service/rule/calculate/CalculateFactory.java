package com.bullish.mall.service.rule.calculate;

import java.util.Optional;

import com.bullish.mall.entity.Discount;
import com.bullish.mall.service.rule.DiscountStrategy;
import com.bullish.mall.util.constant.ProfitEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculateFactory {
  @Autowired ReduceAmountCalculateStrategy reduceAmountCalculateStrategy;

  @Autowired FixAmountCalculateStrategy fixAmountCalculateStrategy;

  @Autowired DefaultCalculateStrategy defaultCalculateStrategy;

  public DiscountStrategy getBean(Discount discount) {
    String type =
        Optional.ofNullable(discount)
            .map(e -> e.getConfig())
            .map(e -> e.getProfitConfig())
            .map(e -> e.getType())
            .orElse("");
    ProfitEnum profitEnum = ProfitEnum.valueOf(type);
    switch (profitEnum) {
      case FIX_AMOUNT:
        return fixAmountCalculateStrategy;
      case REDUCED_AMOUNT:
        return reduceAmountCalculateStrategy;
      default:
        return defaultCalculateStrategy;
    }
  }
}
