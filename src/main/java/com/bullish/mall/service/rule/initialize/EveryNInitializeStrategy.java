package com.bullish.mall.service.rule.initialize;

import com.bullish.mall.dto.result.DiscountItemResult;
import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.entity.BasketItem;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EveryNInitializeStrategy extends AbstractInitializeStrategy {

  @Override
  public void initializeOtherFields(BasketItem basketItem, DiscountResult discountResult) {
    int nth =
        Optional.ofNullable(basketItem.getDiscount())
            .map(e -> e.getConfig())
            .map(e -> e.getConditionConfig())
            .map(e -> e.getAmount())
            .map(e -> e.intValue())
            .orElse(0);
    int count = 1;

    if (nth <= 0) return;

    for (DiscountItemResult discountItemResult : discountResult.getDiscountItemResults()) {
      if (count % nth == 0) {
        discountItemResult.setApplyDiscount(true);
      }
      count += 1;
    }
  }
}
