package com.bullish.mall.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderPreviewDto {
  private BigDecimal amount;

  private BigDecimal actualAmount;

  private BigDecimal discountReduce;

  private Long count;

  private CartDto cart;
}
