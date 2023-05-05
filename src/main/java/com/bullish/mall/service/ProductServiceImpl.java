package com.bullish.mall.service;

import com.bullish.mall.api.request.ProductDto;
import com.bullish.mall.core.product.Product;
import com.bullish.mall.core.product.ProductRepository;
import com.bullish.mall.core.product.Sku;
import com.bullish.mall.core.product.Tag;
import com.bullish.mall.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public Product createProduct(ProductDto productDto) {
        Product product = Product.builder()
                .content(productDto.getContent())
                .name(productDto.getName())
                .tags(productDto.getTags()
                        .stream()
                            .distinct()
                            .map((tag) -> Tag.builder().name(tag).build())
                        .collect(Collectors.toSet())
                )
                .Sku(Sku.builder()
                        .price(productDto.getPrice())
                        .stocks(0L)
                        .build()
                )
                .build();
        return productRepository.save(product);
    }
}
