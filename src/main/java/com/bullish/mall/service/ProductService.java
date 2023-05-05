package com.bullish.mall.service;

import com.bullish.mall.api.request.ProductDto;
import com.bullish.mall.core.product.Product;

public interface ProductService {
    public Product createProduct(ProductDto productDto);
}
