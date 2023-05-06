package com.bullish.mall.dto.param;

import com.bullish.mall.dto.SkuDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductParam {
    @NotBlank
    private String name;

    @NotBlank
    private String content;

    @NotEmpty(message = "need at least one sku")
    @Valid
    private List<SkuDto> skuList;
}
