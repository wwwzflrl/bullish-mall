package com.bullish.mall.service;

import com.bullish.mall.dto.CartDiscountPreviewDto;
import com.bullish.mall.entity.BasketItem;

import java.util.List;

public interface DiscountService {
  CartDiscountPreviewDto calcCartDiscountPreview(List<BasketItem> basketItemList);
}
