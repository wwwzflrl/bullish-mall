package com.bullish.mall.service.rule.initialize;

import com.bullish.mall.dto.result.DiscountItemResult;
import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.entity.BasketItem;
import com.bullish.mall.service.rule.DiscountStrategy;
import java.math.BigDecimal;
import java.util.stream.IntStream;

abstract class AbstractInitializeStrategy implements DiscountStrategy {

  @Override
  public DiscountResult handle(BasketItem basketItem, DiscountResult discountResult) {

    if (discountResult.isValid((o) -> o.getQuantity())) {
      BigDecimal originalPrice =
          discountResult.isValid((o) -> o.getPrice())
              ? basketItem.getSku().getPrice()
              : new BigDecimal(0);
      IntStream.range(1, basketItem.getQuantity().intValue())
          .forEach(
              (e) -> {
                discountResult
                    .getDiscountItemResults()
                    .add(
                        DiscountItemResult.builder()
                            .originalPrice(originalPrice)
                            .currentPrice(originalPrice)
                            .applyDiscount(false)
                            .build());
              });
    }

    this.initializeOtherFields(basketItem, discountResult);
    return discountResult;
  }

  protected abstract void initializeOtherFields(
      BasketItem basketItem, DiscountResult discountResult);

  protected void initializeDiscountItemResults(
      BasketItem basketItem, DiscountResult discountResult) {
    if (!discountResult.isValid((o) -> o.getQuantity())) return;

    BigDecimal originalPrice =
        discountResult.isValid((o) -> o.getPrice())
            ? basketItem.getSku().getPrice()
            : new BigDecimal(0);
    IntStream.range(1, basketItem.getQuantity().intValue())
        .forEach(
            (e) -> {
              discountResult
                  .getDiscountItemResults()
                  .add(
                      DiscountItemResult.builder()
                          .originalPrice(originalPrice)
                          .currentPrice(originalPrice)
                          .applyDiscount(false)
                          .build());
            });
  }
}
