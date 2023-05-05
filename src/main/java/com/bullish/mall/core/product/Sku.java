package com.bullish.mall.core.product;

import com.bullish.mall.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Sku extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long stocks;

    @Column(precision=10, scale=2)
    private BigDecimal price;
}