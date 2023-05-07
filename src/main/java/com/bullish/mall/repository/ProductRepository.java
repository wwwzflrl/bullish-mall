package com.bullish.mall.repository;

import com.bullish.mall.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
  @Override
  @Query("select e from #{#entityName} e where e.deleted=false")
  List<Product> findAll();

  @Override
  <S extends Product> List<S> saveAll(Iterable<S> entities);

  @Transactional
  @Query("update #{#entityName} e set e.deleted=true where e.id=?1")
  @Modifying
  void softDeleteById(Long id);
}
