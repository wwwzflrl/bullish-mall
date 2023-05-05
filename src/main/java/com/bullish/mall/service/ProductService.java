package com.bullish.mall.service;

import com.bullish.mall.api.request.ProductDto;
import com.bullish.mall.core.product.Product;

import java.util.List;

public interface ProductService {

    public List<Product> getProducts();

    public Product createProduct(ProductDto productDto);


    public void deleteProduct(Long id);
}
