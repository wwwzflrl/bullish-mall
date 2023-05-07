package com.bullish.mall.service.rule.valid;

import com.bullish.mall.entity.Discount;
import com.bullish.mall.service.rule.DiscountStrategy;
import com.bullish.mall.util.constant.TargetEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidFactory {
  @Autowired FullProductValidStrategy fullProductValidStrategy;

  @Autowired SpecifiedProductValidStrategy specifiedProductValidStrategy;

  @Autowired DefaultValidStrategy defaultValidStrategy;

  public DiscountStrategy getBean(Discount discount) {
    String type =
        Optional.ofNullable(discount)
            .map(e -> e.getConfig())
            .map(e -> e.getTargetConfig())
            .map(e -> e.getType())
            .orElse("");
    TargetEnum targetEnum = TargetEnum.valueOf(type);
    switch (targetEnum) {
      case FULL_PRODUCT:
        return fullProductValidStrategy;
      case SPECIFIED_PRODUCT:
        return specifiedProductValidStrategy;
      default:
        return defaultValidStrategy;
    }
  }
}
