package com.bullish.mall.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/** To do, implement discount rule */
@Data
@Builder
public class DiscountDto {
  private Long id;

  @NotBlank private String description;

  @NotNull @Valid private DiscountConfigDto config;
}
