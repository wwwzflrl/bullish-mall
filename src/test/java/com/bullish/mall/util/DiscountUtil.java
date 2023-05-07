package com.bullish.mall.util;

import com.bullish.mall.dto.DiscountDto;
import com.bullish.mall.service.rule.DiscountEnum;
import com.bullish.mall.dto.DiscountConfigDto;
import com.bullish.mall.entity.Discount;
import java.math.BigDecimal;

public class DiscountUtil {
  public static DiscountConfigDto createDiscountConfigDto(Integer index) {
    return DiscountConfigDto.builder()
        .type(DiscountEnum.FULL_AMOUNT.name())
        .paramX(new BigDecimal(index))
        .paramY(new BigDecimal(index + 1))
        .build();
  }

  public static DiscountDto getDefaultDiscountDto(Integer index) {
    return DiscountDto.builder()
        .description("description" + index)
        .config(DiscountUtil.createDiscountConfigDto(index))
        .build();
  }

  public static Discount getDefaultDiscount(Integer index) {
    return Discount.builder()
        .description("description" + index)
        .config(DiscountUtil.createDiscountConfigDto(index))
        .build();
  }
}
