package com.bullish.mall.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConditionEnum {
  FULL_AMOUNT("full_amount"),

  FULL_PIECE("full_piece"),

  EVERY_N("every_n");

  private String type;
}
