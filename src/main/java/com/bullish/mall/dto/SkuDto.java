package com.bullish.mall.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuDto {
    private Long id;

    @Min(0)
    @Digits(integer = 8, fraction = 2)
    private BigDecimal price;


    @NotEmpty(message = "need at least one tag")
    @Valid
    private List<@NotBlank String> tags;
}
