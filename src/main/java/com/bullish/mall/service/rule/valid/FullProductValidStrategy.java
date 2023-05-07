package com.bullish.mall.service.rule.valid;

import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.entity.BasketItem;
import org.springframework.stereotype.Service;

@Service
public class FullProductValidStrategy extends AbstractValidStrategy {
  @Override
  protected void validOtherAttribute(BasketItem basketItem, DiscountResult discountResult) {}
}
