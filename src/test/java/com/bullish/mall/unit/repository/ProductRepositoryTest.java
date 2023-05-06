package com.bullish.mall.unit.repository;

import com.bullish.mall.entity.Product;
import com.bullish.mall.repository.ProductRepository;
import com.bullish.mall.repository.SkuRepository;
import com.bullish.mall.util.ProductUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ProductRepositoryTest extends DbTestBase {

  @Autowired ProductRepository productRepository;

  @Autowired SkuRepository skuRepository;

  @PersistenceContext EntityManager entityManager;

  @BeforeEach
  public void setUp() {
    productRepository.deleteAll();
    createProductList();
  }

  @Test
  public void avoidNPlusOne() throws IOException {
    Statistics statistics =
        entityManager.getEntityManagerFactory().unwrap(SessionFactory.class).getStatistics();
    Long beforeExecution = statistics.getQueryExecutionCount();

    List<Product> products = productRepository.findAll();
    ObjectMapper mapper = new ObjectMapper();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    mapper.writeValue(baos, products);

    Long afterExecution = statistics.getQueryExecutionCount();

    Assertions.assertEquals(afterExecution - 1, beforeExecution);
  }

  @Test
  public void deleteProductAlsoRelations() {
    productRepository.deleteAll();

    Long skuCount = skuRepository.count();

    Assertions.assertEquals(skuCount, 0);
  }

  private void createProductList() {
    productRepository.saveAll(
        List.of(ProductUtil.getDefaultProduct(0), ProductUtil.getDefaultProduct(1)));
  }
}
