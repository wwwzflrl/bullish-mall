package com.bullish.mall.service.rule.calculate;

import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.entity.BasketItem;
import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class FixAmountCalculateStrategy extends AbstractCalculateStrategy {

  @Override
  protected void calculateOtherFields(BasketItem basketItem, DiscountResult discountResult) {
    BigDecimal fixAmount =
        Optional.ofNullable(basketItem.getDiscount())
            .map(e -> e.getConfig())
            .map(e -> e.getProfitConfig())
            .map(e -> e.getUnit())
            .orElse(BigDecimal.ZERO);

    discountResult
        .getDiscountItemResults()
        .forEach(
            (o) -> {
              if (o.getApplyDiscount()) o.setCurrentPrice(fixAmount);
              else o.setCurrentPrice(o.getOriginalPrice());
            });
  }
}