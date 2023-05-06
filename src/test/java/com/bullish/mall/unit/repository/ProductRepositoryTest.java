package com.bullish.mall.unit.repository;

import com.bullish.mall.entity.Product;
import com.bullish.mall.entity.Sku;
import com.bullish.mall.repository.ProductRepository;
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
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductRepositoryTest extends DbTestBase {

    @Autowired
    ProductRepository productRepository;

    @PersistenceContext
    EntityManager entityManager;

    @BeforeEach
    public void setUp() throws Exception {
        productRepository.deleteAll();
    }

    @Test
    public void avoid_n_plus_1() throws IOException {
        createProducts();
        Statistics statistics = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class).getStatistics();
        Long beforeExecution = statistics.getQueryExecutionCount();

        List<Product> products = productRepository.findAll();
        ObjectMapper mapper = new ObjectMapper();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mapper.writeValue(baos, products);

        Long afterExecution = statistics.getQueryExecutionCount();

        Assertions.assertEquals(afterExecution - 1, beforeExecution);
    }

    private void createProducts() {
        productRepository.save(Product.builder()
                .name("name1")
                .content("content1")
                .sku(List.of(Sku.builder()
                        .price(new BigDecimal(200))
                        .tags(Set.of("Tag1"))
                        .build()
                ))
                .build());
        productRepository.save(Product.builder()
                .name("name2")
                .content("content2")
                .sku(List.of(Sku.builder()
                        .tags(Set.of("Tag2"))
                        .price(new BigDecimal(300))
                        .build()
                ))
                .build());
    }
}
