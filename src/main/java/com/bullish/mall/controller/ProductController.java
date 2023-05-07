package com.bullish.mall.controller;

import com.bullish.mall.security.IsAdmin;
import com.bullish.mall.dto.DiscountDto;
import com.bullish.mall.dto.ProductDto;
import com.bullish.mall.dto.SkuDto;
import com.bullish.mall.dto.param.ProductParam;
import com.bullish.mall.entity.Discount;
import com.bullish.mall.entity.Product;
import com.bullish.mall.entity.Sku;
import com.bullish.mall.repository.DiscountRepository;
import com.bullish.mall.repository.ProductRepository;
import com.bullish.mall.service.rule.target.TargetFactory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {
  @Autowired ProductRepository productRepository;

  @Autowired DiscountRepository discountRepository;

  @Autowired TargetFactory targetFactory;

  @GetMapping
  public ResponseEntity getProductList() {
    List<Product> productList = productRepository.findAll();
    List<Discount> discountList = discountRepository.findAll();
    List<ProductDto> productDtoList =
        productList.stream()
            .map(
                (product) ->
                    ProductDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .content(product.getContent())
                        .skuList(
                            product.getSkuList().stream()
                                .map(
                                    (sku) ->
                                        SkuDto.builder()
                                            .id(sku.getId())
                                            .price(sku.getPrice())
                                            .tags(sku.getTags().stream().toList())
                                            .build())
                                .collect(Collectors.toList()))
                        .discountList(
                            discountList.stream()
                                .filter(
                                    discount ->
                                        targetFactory
                                            .getBean(
                                                discount.getConfig().getTargetConfig().getType())
                                            .map((strategy) -> strategy.isTarget(product, discount))
                                            .orElseGet(() -> false))
                                .map(
                                    (discount ->
                                        DiscountDto.builder()
                                            .description(discount.getDescription())
                                            .id(discount.getId())
                                            .config(discount.getConfig())
                                            .build()))
                                .collect(Collectors.toList()))
                        .build())
            .collect(Collectors.toList());
    return ResponseEntity.ok(productDtoList);
  }

  @IsAdmin
  @PostMapping
  public ResponseEntity createProduct(@Valid @RequestBody ProductParam productParam) {
    Product product =
        Product.builder().content(productParam.getContent()).name(productParam.getName()).build();
    product.setSkuList(
        productParam.getSkuList().stream()
            .map(
                (sku) ->
                    Sku.builder()
                        .tags(sku.getTags().stream().collect(Collectors.toSet()))
                        .price(sku.getPrice())
                        .product(product)
                        .build())
            .collect(Collectors.toList()));
    return ResponseEntity.ok(productRepository.save(product));
  }

  @IsAdmin
  @DeleteMapping("/{id}")
  public void deleteProduct(@PathVariable("id") Long id) {
    productRepository.softDeleteById(id);
  }

  @IsAdmin
  @PostMapping("/discount")
  public ResponseEntity createDiscount(@Valid @RequestBody DiscountDto discountDto) {
    Discount discount =
        discountRepository.save(
            Discount.builder()
                .description(discountDto.getDescription())
                .config(discountDto.getConfig())
                .build());
    return ResponseEntity.ok(discount);
  }

  @IsAdmin
  @DeleteMapping("/discount/{id}")
  public void deletedDiscount(@PathVariable("id") Long id) {
    discountRepository.softDeleteById(id);
  }
}
