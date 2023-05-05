package com.bullish.mall.api.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    @NotBlank(message = "can't be empty")
    private String name;

    @NotBlank(message = "can't be empty")
    private String content;

    @Min(0)
    @Digits(integer = 8, fraction = 2)
    private BigDecimal price;

    @NotEmpty(message = "need at least one tag")
    private List<@NotBlank() String> tags;
}
