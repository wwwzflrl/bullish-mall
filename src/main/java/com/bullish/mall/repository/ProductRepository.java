package com.bullish.mall.repository;

import com.bullish.mall.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    @Override
    List<Product> findAll();

    @Override
    <S extends Product> List<S> saveAll(Iterable<S> entities);
}
