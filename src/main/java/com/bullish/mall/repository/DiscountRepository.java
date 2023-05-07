package com.bullish.mall.repository;

import com.bullish.mall.entity.Discount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountRepository extends CrudRepository<Discount, Long> {
  @Override
  @Query("select e from #{#entityName} e where e.deleted=false")
  List<Discount> findAll();

  @Override
  List<Discount> findAllById(Iterable<Long> ids);

  @Override
  <S extends Discount> List<S> saveAll(Iterable<S> entities);

  @Transactional
  @Query("update #{#entityName} e set e.deleted=true where e.id=?1")
  @Modifying
  void softDeleteById(Long id);
}
