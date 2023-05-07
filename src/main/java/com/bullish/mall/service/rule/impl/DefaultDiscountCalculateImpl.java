package com.bullish.mall.service.rule.impl;

import com.bullish.mall.dto.DiscountCalculateDto;
import com.bullish.mall.entity.BasketItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DefaultDiscountCalculateImpl extends AbstractDiscountCalculateImpl {
  @Override
  protected void validateOriginal(
      BasketItem basketItem, DiscountCalculateDto discountCalculateDto) {
    // set failure reason
    discountCalculateDto.setValid(false);
    discountCalculateDto.setUnit(0);
    discountCalculateDto.getReasons().add("No valid discount rule found");

    // calculate original data
    discountCalculateDto.setQuantity(basketItem.getQuantity());
    discountCalculateDto.setPrice(basketItem.getSku().getPrice());
    discountCalculateDto.calculateOriginalAmout();
  }

  @Override
  protected void calculate(BasketItem basketItem, DiscountCalculateDto discountCalculateDto) {
    discountCalculateDto.setDiscountAmount(BigDecimal.ZERO);
    discountCalculateDto.setPayAmount(discountCalculateDto.getOriginalAmount());
  }
}
