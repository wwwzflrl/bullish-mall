package com.bullish.mall.dto.config;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ConditionConfig extends CommonConfig {
  @NotNull private Long unit;

  @NotNull
  @Min(0)
  @Digits(integer = 8, fraction = 2)
  private BigDecimal amount;
}
