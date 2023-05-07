package com.bullish.mall.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CartDiscountPreviewDto {
  /** Original how much you need ti pay */
  private BigDecimal originalAmount;

  /** How much being reduced */
  private BigDecimal discountAmount;

  /** How much you need to pay after discount */
  private BigDecimal payAmount;

  private Long quantity;

  private List<DiscountCalculateDto> discountCalculateDtoList;
}
