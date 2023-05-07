package com.bullish.mall.service.rule.valid;

import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.dto.result.ValidResult;
import com.bullish.mall.entity.BasketItem;
import com.bullish.mall.entity.Product;
import com.bullish.mall.entity.Sku;
import com.bullish.mall.service.rule.DiscountStrategy;

abstract class AbstractValidStrategy implements DiscountStrategy {
  @Override
  public DiscountResult handle(BasketItem basketItem, DiscountResult discountResult) {
    this.validSku(basketItem, discountResult);
    this.validProduct(basketItem, discountResult);
    return discountResult;
  }

  protected void validProduct(BasketItem basketItem, DiscountResult discountResult) {
    Product product = basketItem.getProduct();
    if (product == null || product.getDeleted()) {
      discountResult.addValidResult(
          ValidResult.builder().quantity(false).build(), "Invalid product. No quantity any more");
    }
  }

  protected void validSku(BasketItem basketItem, DiscountResult discountResult) {
    Sku sku = basketItem.getSku();
    if (sku == null || sku.getPrice() == null) {
      discountResult.addValidResult(
          ValidResult.builder().price(false).build(), "Invalid sku price. Set to zero");
    }
  }

  protected abstract void validOtherAttribute(BasketItem basketItem, DiscountResult discountResult);
}
