package com.bullish.mall.service.impl;

import com.bullish.mall.dto.DiscountCalculateDto;
import com.bullish.mall.dto.CartDiscountPreviewDto;
import com.bullish.mall.entity.BasketItem;
import com.bullish.mall.service.DiscountService;
import com.bullish.mall.service.rule.CalculateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {
  @Autowired private CalculateFactory calculateFactory;

  @Override
  public CartDiscountPreviewDto calcCartDiscountPreview(List<BasketItem> basketItemList) {
    List<DiscountCalculateDto> discountCalculateDtoList =
        basketItemList.stream()
            .map(
                basketItem -> calculateFactory.getBean(basketItem.getDiscount()).handle(basketItem))
            .collect(Collectors.toList());
    CartDiscountPreviewDto previewDto =
        CartDiscountPreviewDto.builder()
            .discountCalculateDtoList(discountCalculateDtoList)
            .discountAmount(BigDecimal.ZERO)
            .originalAmount(BigDecimal.ZERO)
            .quantity(0L)
            .payAmount(BigDecimal.ZERO)
            .build();

    for (DiscountCalculateDto calculateDto : discountCalculateDtoList) {
      previewDto.setDiscountAmount(
          previewDto.getDiscountAmount().add(calculateDto.getDiscountAmount()));
      previewDto.setPayAmount(previewDto.getPayAmount().add(calculateDto.getPayAmount()));
      previewDto.setOriginalAmount(
          previewDto.getOriginalAmount().add(calculateDto.getOriginalAmount()));
      previewDto.setQuantity(previewDto.getQuantity() + calculateDto.getQuantity());
    }

    previewDto.setDiscountAmount(
        previewDto.getDiscountAmount().setScale(2, RoundingMode.HALF_EVEN));
    previewDto.setPayAmount(previewDto.getPayAmount().setScale(2, RoundingMode.HALF_EVEN));
    previewDto.setOriginalAmount(
        previewDto.getOriginalAmount().setScale(2, RoundingMode.HALF_EVEN));
    return previewDto;
  }
}
