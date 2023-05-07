package com.bullish.mall.service.rule.impl;

import com.bullish.mall.dto.DiscountCalculateDto;
import com.bullish.mall.entity.BasketItem;
import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class FullPieceDiscountCalculateImpl extends AbstractDiscountCalculateImpl {
  @Override
  protected void validateOriginal(
      BasketItem basketItem, DiscountCalculateDto discountCalculateDto) {
    // calculate original data
    discountCalculateDto.setQuantity(basketItem.getQuantity());
    discountCalculateDto.setPrice(basketItem.getSku().getPrice());
    discountCalculateDto.calculateOriginalAmout();

    BigDecimal expectedQuantity =
        Optional.ofNullable(basketItem.getDiscount())
            .map(o -> o.getConfig())
            .map(o -> o.getParamX())
            .orElse(BigDecimal.ZERO);

    if (expectedQuantity.compareTo(BigDecimal.ZERO) <= 0
        || expectedQuantity.compareTo(new BigDecimal(discountCalculateDto.getQuantity())) > 0) {
      discountCalculateDto.setValid(false);
      discountCalculateDto.setUnit(0);
      discountCalculateDto.getReasons().add("No meet full quantity" + expectedQuantity);
      return;
    }
    discountCalculateDto.setValid(true);
    discountCalculateDto.setUnit(1);
  }

  @Override
  protected void calculate(BasketItem basketItem, DiscountCalculateDto discountCalculateDto) {
    BigDecimal discountAmount =
        Optional.ofNullable(basketItem.getDiscount())
            .map(o -> o.getConfig())
            .map(o -> o.getParamY())
            .orElse(BigDecimal.ZERO);
    discountCalculateDto.setDiscountAmount(discountAmount);
    discountCalculateDto.setPayAmount(
        discountCalculateDto.getOriginalAmount().subtract(discountAmount));
  }
}
