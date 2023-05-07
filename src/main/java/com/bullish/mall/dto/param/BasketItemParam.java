package com.bullish.mall.dto.param;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketItemParam {
  @NotNull
  @Min(0)
  @Digits(integer = 8, fraction = 0)
  private Long quantities;

  @NotNull
  @Min(1)
  private Long productId;

  @NotNull
  @Min(1)
  private Long skuId;

  @Min(1)
  private Long discountId;
}
