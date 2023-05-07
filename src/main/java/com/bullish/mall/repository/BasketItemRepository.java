package com.bullish.mall.repository;

import com.bullish.mall.entity.BasketItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketItemRepository extends CrudRepository<BasketItem, Long> {
  @Override
  List<BasketItem> findAll();

  @Override
  List<BasketItem> findAllById(Iterable<Long> ids);

  List<BasketItem> findByUserId(Long userId);

  @Override
  <S extends BasketItem> List<S> saveAll(Iterable<S> entities);
}
