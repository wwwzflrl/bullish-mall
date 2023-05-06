package com.bullish.mall.util;

import com.bullish.mall.constant.ConditionEnum;
import com.bullish.mall.constant.ProfitEnum;
import com.bullish.mall.constant.TargetEnum;
import com.bullish.mall.dto.DiscountConfigDto;
import com.bullish.mall.dto.config.ConditionConfig;
import com.bullish.mall.dto.config.ProfitConfig;
import com.bullish.mall.dto.config.TargetConfig;
import com.bullish.mall.entity.Discount;
import java.math.BigDecimal;
import java.util.List;

public class DiscountUtil {
    public static DiscountConfigDto createDiscountConfigDto(Integer index) {
        return DiscountConfigDto.builder()
                .conditionConfig(ConditionConfig.builder()
                        .type(ConditionEnum.FULL_PIECE.name())
                        .amount(new BigDecimal(100 + index))
                        .unit(index.longValue())
                        .build()
                ).targetConfig(TargetConfig.builder()
                        .type(TargetEnum.FULL_PRODUCT.name())
                        .productIds(List.of(index.longValue()))
                        .build()
                ).profitConfig(ProfitConfig.builder()
                        .type(ProfitEnum.FIX_AMOUNT.name())
                        .unit(new BigDecimal(30 + index))
                        .build()
                ).build();
    }

    public static Discount getDefaultDiscount(Integer index) {
        return Discount.builder()
                .description("description" + index)
                .config(DiscountUtil.createDiscountConfigDto(index))
                .build();

    }
}
