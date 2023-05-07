package com.bullish.mall.service.impl;

import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.entity.BasketItem;
import com.bullish.mall.service.DiscountService;
import com.bullish.mall.service.rule.condition.ConditionFactory;
import com.bullish.mall.service.rule.profit.ProfitFactory;
import com.bullish.mall.service.rule.target.TargetFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {
  @Autowired private ProfitFactory profitFactory;

  @Autowired private ConditionFactory conditionFactory;

  @Autowired private TargetFactory targetFactory;

  @Override
  public List<DiscountResult> calcDiscountResults(List<BasketItem> basketItemList) {
    return basketItemList.stream()
        .map(
            (basketItem -> {
              return DiscountResult.builder().build();
            }))
        .collect(Collectors.toList());
  }
}
