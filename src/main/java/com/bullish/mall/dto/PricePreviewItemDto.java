package com.bullish.mall.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PricePreviewItemDto {
    private Boolean pass;

    private String[] reason;

    private BigDecimal amount;

    private BigDecimal actualAmount;

    private BigDecimal discountReduce;

    private Long count;

}
