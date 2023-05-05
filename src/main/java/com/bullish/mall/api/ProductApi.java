package com.bullish.mall.api;

import com.bullish.mall.api.request.ProductDto;
import com.bullish.mall.api.security.IsAdmin;
import com.bullish.mall.core.product.Product;
import com.bullish.mall.core.product.ProductRepository;
import com.bullish.mall.core.product.Sku;
import com.bullish.mall.core.product.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductApi {
    @Autowired
    ProductRepository productRepository;

    @GetMapping
    public ResponseEntity getProductList() {
        List<Product> products = productRepository.findAll();;
        return ResponseEntity.ok(products);
    }

    @IsAdmin
    @PostMapping
    public ResponseEntity createProduct(@Valid @RequestBody ProductDto productDto) {
        Product product = productRepository.save(Product
                .builder()
                .content(productDto.getContent())
                .name(productDto.getName())
                .tags(productDto.getTags()
                        .stream()
                        .distinct()
                        .map((tag) -> Tag.builder().name(tag).build())
                        .collect(Collectors.toSet())
                )
                .sku(Sku.builder()
                        .price(productDto.getPrice())
                        .stocks(0L)
                        .build()
                )
                .build()
        );
        return ResponseEntity.ok(product);
    }

    @IsAdmin
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
    }
}
