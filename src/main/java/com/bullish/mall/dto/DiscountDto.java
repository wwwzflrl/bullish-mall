package com.bullish.mall.dto;

import lombok.Builder;
import lombok.Data;

/**
 * To do, implement discount rule
 */
@Data
@Builder
public class DiscountDto {
    private Long id;

    private String type;

    private String requirement;

    private String results;

    private String description;
}
