package com.bullish.mall.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductDto {
  private Long id;

  private String name;

  private String content;

  private List<SkuDto> skuList;

  private List<DiscountDto> discountList;
}
