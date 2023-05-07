package com.bullish.mall.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DiscountCalculateDto {
  private List<String> reasons = new ArrayList<>();

  private Boolean valid = false;

  private Integer unit = 0;

  private Long quantity = 0L;

  private BigDecimal price = BigDecimal.ZERO;

  /** Original how much you need ti pay */
  private BigDecimal originalAmount = BigDecimal.ZERO;

  /** How much being reduced */
  private BigDecimal discountAmount = BigDecimal.ZERO;

  /** How much you need to pay after discount */
  private BigDecimal payAmount = BigDecimal.ZERO;

  public void calculateOriginalAmout() {
    originalAmount = price.multiply(new BigDecimal(quantity));
  }
}
