package com.bullish.mall.util.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TargetEnum {
  DEFAULT("default"),
  FULL_PRODUCT("full_product"),

  SPECIFIED_PRODUCT("specific_product");

  private String type;
}
