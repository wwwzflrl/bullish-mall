package com.bullish.mall.service.rule.initialize;

import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.entity.BasketItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class FullPieceInitializeStrategy extends AbstractInitializeStrategy {

  @Override
  public void initializeOtherFields(BasketItem basketItem, DiscountResult discountResult) {
    BigDecimal originalQuantity = new BigDecimal(discountResult.getDiscountItemResults().size());
    BigDecimal expectedQuantity =
        Optional.ofNullable(basketItem.getDiscount())
            .map(o -> o.getConfig())
            .map(o -> o.getConditionConfig())
            .map(o -> o.getAmount())
            .orElse(new BigDecimal(0));
    if (expectedQuantity.compareTo(originalQuantity) > 0) return;

    discountResult
        .getDiscountItemResults()
        .forEach(
            discountItemResult -> {
              discountItemResult.setApplyDiscount(true);
            });
  }
}
