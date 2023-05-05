package com.bullish.mall.api;

import com.bullish.mall.api.request.ProductDto;
import com.bullish.mall.api.security.IsAdmin;
import com.bullish.mall.core.product.Product;
import com.bullish.mall.core.user.User;
import com.bullish.mall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductApi {
    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity getProductList() {
        List<Product> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @IsAdmin
    @PostMapping
    public ResponseEntity createProduct(@Valid @RequestBody ProductDto productDto) {
        Product product = productService.createProduct(productDto);
        return ResponseEntity.ok(product);
    }

    @IsAdmin
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        productService.deleteProduct(id);
    }
}
