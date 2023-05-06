package com.bullish.mall.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TargetEnum {

    FULL_PRODUCT("full_product"),

    SPECIFIED_PRODUCT("specific_product");

    private String type;
}
