package com.bullish.mall.service.rule.valid;

import com.bullish.mall.dto.config.TargetConfig;
import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.dto.result.ValidResult;
import com.bullish.mall.entity.BasketItem;
import org.springframework.stereotype.Service;

@Service
public class SpecifiedProductValidStrategy extends AbstractValidStrategy {

  @Override
  protected void validOtherAttribute(BasketItem basketItem, DiscountResult discountResult) {
    super.handle(basketItem, discountResult);
    Long productId = basketItem.getProduct().getId();
    TargetConfig targetConfig = basketItem.getDiscount().getConfig().getTargetConfig();
    if (targetConfig.getProductIds() == null
        || targetConfig.getProductIds().stream().noneMatch((id) -> productId.equals(id))) {
      discountResult.addValidResult(
          ValidResult.builder().discount(false).build(), "Invalid discount rule, disable it");
    }
  }
}
