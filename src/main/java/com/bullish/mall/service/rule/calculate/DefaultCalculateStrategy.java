package com.bullish.mall.service.rule.calculate;

import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.dto.result.ValidResult;
import com.bullish.mall.entity.BasketItem;
import org.springframework.stereotype.Service;

@Service
public class DefaultCalculateStrategy extends AbstractCalculateStrategy {

  @Override
  protected void calculateOtherFields(BasketItem basketItem, DiscountResult discountResult) {

    discountResult
        .getDiscountItemResults()
        .forEach(
            (e) -> {
              e.setCurrentPrice(e.getOriginalPrice());
              e.setApplyDiscount(false);
            });

    discountResult.addValidResult(
        ValidResult.builder().discount(false).build(), "Unknown rules. Disable discount");
  }
}
