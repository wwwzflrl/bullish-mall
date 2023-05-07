package com.bullish.mall.dto.result;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DiscountResult {
  private Boolean pass;

  @Builder.Default private List<String> reason = new ArrayList<>();

  private BigDecimal originalAmount;

  private BigDecimal actualAmount;

  private BigDecimal discountReduce;

  private Long count;
}
