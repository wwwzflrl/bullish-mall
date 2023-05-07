package com.bullish.mall.repository;

import com.bullish.mall.entity.Sku;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkuRepository extends CrudRepository<Sku, Long> {
  @Override
  List<Sku> findAllById(Iterable<Long> ids);
}
