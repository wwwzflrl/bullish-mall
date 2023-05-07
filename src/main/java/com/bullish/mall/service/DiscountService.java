package com.bullish.mall.service;

import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.entity.BasketItem;

import java.util.List;

public interface DiscountService {
  List<DiscountResult> calcDiscountResults(List<BasketItem> basketItemList);
}
