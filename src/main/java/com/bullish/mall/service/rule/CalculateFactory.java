package com.bullish.mall.service.rule;

import java.util.Optional;

import com.bullish.mall.entity.Discount;
import com.bullish.mall.service.rule.impl.DefaultDiscountCalculateImpl;
import com.bullish.mall.service.rule.impl.EveryNDiscountCalculateImpl;
import com.bullish.mall.service.rule.impl.FullAmountDiscountCalculateImpl;
import com.bullish.mall.service.rule.impl.FullPieceDiscountCalculateImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculateFactory {
  @Autowired FullPieceDiscountCalculateImpl fullPieceDiscountCalculate;

  @Autowired FullAmountDiscountCalculateImpl fullAmountDiscountCalculate;

  @Autowired DefaultDiscountCalculateImpl defaultDiscountCalculate;

  @Autowired EveryNDiscountCalculateImpl everyNDiscountCalculate;

  public DiscountCalculate getBean(Discount discount) {
    String type =
        Optional.ofNullable(discount)
            .map(e -> e.getConfig())
            .map(e -> e.getType())
            .orElse(DiscountEnum.NONE.getType());
    DiscountEnum profitEnum = DiscountEnum.valueOf(type);
    switch (profitEnum) {
      case FULL_AMOUNT:
        return fullAmountDiscountCalculate;
      case FULL_PIECE:
        return fullPieceDiscountCalculate;
      case EVERY_N_RATE:
        return everyNDiscountCalculate;
      default:
        return defaultDiscountCalculate;
    }
  }
}
