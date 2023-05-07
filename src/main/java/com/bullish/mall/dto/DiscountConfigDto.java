package com.bullish.mall.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountConfigDto {

  @NotNull @NotBlank private String type;

  @NotNull
  @Digits(integer = 8, fraction = 2)
  private BigDecimal paramX;

  @NotNull
  @Digits(integer = 8, fraction = 2)
  private BigDecimal paramY;
}
