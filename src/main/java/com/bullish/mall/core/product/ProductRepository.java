package com.bullish.mall.core.product;

import com.bullish.mall.core.product.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Override
    Optional<Product> findById(Long id);
}
