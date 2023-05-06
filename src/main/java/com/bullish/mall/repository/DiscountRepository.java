package com.bullish.mall.repository;

import com.bullish.mall.entity.Discount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends CrudRepository<Discount, Long> {
    @Override
    List<Discount> findAll();

    @Override
    <S extends Discount> List<S> saveAll(Iterable<S> entities);
}