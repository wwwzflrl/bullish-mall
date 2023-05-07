package com.bullish.mall.service.rule.initialize;

import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.entity.BasketItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class FullAmountInitializeStrategy extends AbstractInitializeStrategy {

  @Override
  public void initializeOtherFields(BasketItem basketItem, DiscountResult discountResult) {
    BigDecimal originalAmount = discountResult.calculateOriginalAmount();
    BigDecimal expectedAmount =
        Optional.ofNullable(basketItem.getDiscount())
            .map(o -> o.getConfig())
            .map(o -> o.getConditionConfig())
            .map(o -> o.getAmount())
            .orElse(new BigDecimal(0));
    if (expectedAmount.compareTo(originalAmount) > 0) return;

    discountResult
        .getDiscountItemResults()
        .forEach(
            discountItemResult -> {
              discountItemResult.setApplyDiscount(true);
            });
  }
}
