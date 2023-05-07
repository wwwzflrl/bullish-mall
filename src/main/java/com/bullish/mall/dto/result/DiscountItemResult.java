package com.bullish.mall.dto.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DiscountItemResult {
  private BigDecimal originalPrice;

  private BigDecimal currentPrice;

  private Boolean applyDiscount;
}
