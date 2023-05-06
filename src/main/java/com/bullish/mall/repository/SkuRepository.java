package com.bullish.mall.repository;

import com.bullish.mall.entity.Sku;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuRepository extends CrudRepository<Sku, Long> {}
