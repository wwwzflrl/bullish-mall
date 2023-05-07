package com.bullish.mall.util.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProfitEnum {
  DEFAULT("default"),

  REDUCED_AMOUNT("reduce_amount"),

  FIX_AMOUNT("fix_amount");

  private String type;
}
