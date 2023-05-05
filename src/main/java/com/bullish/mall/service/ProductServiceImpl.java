package com.bullish.mall.service;

import com.bullish.mall.api.request.ProductDto;
import com.bullish.mall.core.product.Product;
import com.bullish.mall.core.product.ProductRepository;
import com.bullish.mall.core.product.Sku;
import com.bullish.mall.core.product.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        List<Product> products = StreamSupport
                .stream(productRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return products;
    }

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

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
