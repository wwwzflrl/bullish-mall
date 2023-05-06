package com.bullish.mall.dto;

import com.bullish.mall.dto.config.ConditionConfig;
import com.bullish.mall.dto.config.ProfitConfig;
import com.bullish.mall.dto.config.TargetConfig;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountConfigDto {
    @NotNull
    @Valid
    private ConditionConfig conditionConfig;

    @NotNull
    @Valid
    private ProfitConfig profitConfig;

    @NotNull
    @Valid
    private TargetConfig targetConfig;
}
