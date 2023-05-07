package com.bullish.mall.service.rule.impl;

import com.bullish.mall.dto.DiscountCalculateDto;
import com.bullish.mall.entity.BasketItem;
import com.bullish.mall.service.rule.DiscountCalculate;

abstract class AbstractDiscountCalculateImpl implements DiscountCalculate {

  @Override
  public DiscountCalculateDto handle(BasketItem basketItem) {
    DiscountCalculateDto discountCalculateDto = new DiscountCalculateDto();

    /** Check Whether current basket can enjoy discount */
    validate(basketItem, discountCalculateDto);

    /** Do the calculation after check base on different handler */
    this.calculate(basketItem, discountCalculateDto);

    return discountCalculateDto;
  }

  protected void validate(BasketItem basketItem, DiscountCalculateDto discountCalculateDto) {
    if (basketItem.getProduct() == null
        || basketItem.getProduct().getDeleted()
        || basketItem.getSku().getPrice() == null) {
      discountCalculateDto.setValid(false);
      discountCalculateDto.setUnit(0);
      discountCalculateDto.getReasons().add("No valid produce / stock price, ignore this basket");
      return;
    }
    validateOriginal(basketItem, discountCalculateDto);
  }

  protected abstract void calculate(
      BasketItem basketItem, DiscountCalculateDto discountCalculateDto);

  protected abstract void validateOriginal(
      BasketItem basketItem, DiscountCalculateDto discountCalculateDto);
}
