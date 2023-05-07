package com.bullish.mall.service.rule.initialize;

import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.dto.result.ValidResult;
import com.bullish.mall.entity.BasketItem;
import org.springframework.stereotype.Service;

@Service
public class DefaultInitializeStrategy extends AbstractInitializeStrategy {
  @Override
  public void initializeOtherFields(BasketItem basketItem, DiscountResult discountResult) {
    discountResult.addValidResult(
        ValidResult.builder().discount(false).build(), "Unknown rules. Disable discount");
  }
}
