package com.bullish.mall.util.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProfitEnum {
  REDUCED_AMOUNT("reduce_amount"),

  REDUCED_RATE("reduce_rate"),

  FIX_AMOUNT("fix_amount");

  private String type;
}
