package com.bullish.mall.dto;

import com.bullish.mall.core.product.Tag;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class SkuDto {
    private Long id;

    private BigDecimal price;

    private Set<Tag> tags;
}
