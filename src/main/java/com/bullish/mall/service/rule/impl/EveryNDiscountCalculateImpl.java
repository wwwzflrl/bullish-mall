package com.bullish.mall.service.rule.impl;

import com.bullish.mall.dto.DiscountCalculateDto;
import com.bullish.mall.entity.BasketItem;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EveryNDiscountCalculateImpl extends AbstractDiscountCalculateImpl {
  @Override
  protected void validateOriginal(
      BasketItem basketItem, DiscountCalculateDto discountCalculateDto) {
    // calculate original data
    discountCalculateDto.setQuantity(basketItem.getQuantity());
    discountCalculateDto.setPrice(basketItem.getSku().getPrice());
    discountCalculateDto.calculateOriginalAmout();

    BigDecimal nth =
        Optional.ofNullable(basketItem.getDiscount())
            .map(o -> o.getConfig())
            .map(o -> o.getParamX())
            .orElse(BigDecimal.ZERO);

    int status = new BigDecimal(basketItem.getQuantity()).divide(nth).intValue();

    if (status <= 0) {
      discountCalculateDto.setValid(false);
      discountCalculateDto.setUnit(0);
      discountCalculateDto.getReasons().add("No meet every nth" + nth);
      return;
    }
    discountCalculateDto.setValid(true);
    discountCalculateDto.setUnit(status);
  }

  @Override
  protected void calculate(BasketItem basketItem, DiscountCalculateDto discountCalculateDto) {
    BigDecimal discountPercent =
        Optional.ofNullable(basketItem.getDiscount())
            .map(o -> o.getConfig())
            .map(o -> o.getParamY())
            .orElse(BigDecimal.ZERO);
    BigDecimal discountAmount =
        new BigDecimal(discountCalculateDto.getUnit())
            .multiply(discountPercent)
            .multiply(discountCalculateDto.getPrice());
    discountCalculateDto.setDiscountAmount(discountAmount);
    discountCalculateDto.setPayAmount(
        discountCalculateDto.getOriginalAmount().subtract(discountAmount));
  }
}
