package com.bullish.mall.service.impl;

import com.bullish.mall.dto.result.DiscountResult;
import com.bullish.mall.entity.BasketItem;
import com.bullish.mall.service.DiscountService;
import com.bullish.mall.service.rule.initialize.InitializeFactory;
import com.bullish.mall.service.rule.calculate.CalculateFactory;
import com.bullish.mall.service.rule.valid.ValidFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {
  @Autowired private CalculateFactory calculateFactory;

  @Autowired private InitializeFactory initializeFactory;

  @Autowired private ValidFactory validFactory;

  @Override
  public List<DiscountResult> calcDiscountResults(List<BasketItem> basketItemList) {
    return basketItemList.stream()
        .map(
            (basketItem -> {
              DiscountResult discountResult = new DiscountResult();
              validFactory.getBean(basketItem.getDiscount()).handle(basketItem, discountResult);
              initializeFactory
                  .getBean(basketItem.getDiscount())
                  .handle(basketItem, discountResult);
              calculateFactory.getBean(basketItem.getDiscount()).handle(basketItem, discountResult);
              return discountResult;
            }))
        .collect(Collectors.toList());
  }
}
