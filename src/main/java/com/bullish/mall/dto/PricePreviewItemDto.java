package com.bullish.mall.dto;

import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.entity.BasketItem;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PricePreviewItemDto {
  private DiscountResult discountResult;

  private BasketItem BasketItem;
}
