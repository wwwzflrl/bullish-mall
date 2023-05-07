package com.bullish.mall.service.rule.valid;

import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.dto.result.ValidResult;
import com.bullish.mall.entity.BasketItem;
import org.springframework.stereotype.Service;

@Service
public class DefaultValidStrategy extends AbstractValidStrategy {
  @Override
  protected void validOtherAttribute(BasketItem basketItem, DiscountResult discountResult) {
    super.handle(basketItem, discountResult);
    discountResult.addValidResult(
        ValidResult.builder().discount(false).build(), "Invalid discount rule. Disable discount");
  }
}
