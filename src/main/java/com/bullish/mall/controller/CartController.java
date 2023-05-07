package com.bullish.mall.controller;

import com.bullish.mall.dto.param.BasketItemParam;
import com.bullish.mall.entity.*;
import com.bullish.mall.repository.BasketItemRepository;
import com.bullish.mall.repository.DiscountRepository;
import com.bullish.mall.repository.SkuRepository;
import com.bullish.mall.util.ValidList;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class CartController {
  @Autowired private BasketItemRepository basketItemRepository;

  @Autowired private SkuRepository skuRepository;

  @Autowired private DiscountRepository discountRepository;

  @Autowired private Validator validator;

  @GetMapping
  public ResponseEntity getBasketList(@AuthenticationPrincipal User user) {
    List<BasketItem> basketItemList = basketItemRepository.findByUserId(user.getId());
    return ResponseEntity.ok(basketItemList);
  }

  @PostMapping
  public ResponseEntity createBasketList(
      @AuthenticationPrincipal User user,
      @Valid @RequestBody ValidList<BasketItemParam> basketItemParams) {

    Map<Long, Sku> skuMap =
        skuRepository
            .findAllById(
                basketItemParams.stream()
                    .map((item) -> item.getSkuId())
                    .collect(Collectors.toList()))
            .stream()
            .collect(Collectors.toMap(Sku::getId, Function.identity()));

    Map<Long, Discount> discountMap =
        discountRepository
            .findAllById(
                basketItemParams.stream()
                    .map((item) -> item.getDiscountId())
                    .collect(Collectors.toList()))
            .stream()
            .collect(Collectors.toMap(Discount::getId, Function.identity()));

    List<BasketItem> basketItemList =
        basketItemParams.stream()
            .map(
                (param) ->
                    BasketItem.builder()
                        .user(user)
                        .sku(Optional.ofNullable(skuMap.get(param.getSkuId())).orElseThrow())
                        .product(skuMap.get(param.getSkuId()).getProduct())
                        .discount(discountMap.get(param.getDiscountId()))
                        .build())
            .collect(Collectors.toList());
    return ResponseEntity.ok(basketItemRepository.saveAll(basketItemList));
  }

  @DeleteMapping
  public void deleteBasketList(
      @AuthenticationPrincipal User user, @Valid @RequestBody ValidList<@NotBlank Long> ids) {
    List<BasketItem> basketItemList = basketItemRepository.findAllById(ids);
    if (basketItemList.stream()
        .anyMatch((basketItem -> basketItem.getUser().getId() != user.getId()))) {
      throw new AccessDeniedException("Can't delete basket not belong to you");
    }
    basketItemRepository.deleteAllById(ids);
  }
}
