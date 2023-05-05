package com.bullish.mall.core.product;

import com.bullish.mall.core.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String content;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Tag> tags;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sku_id", referencedColumnName = "id")
    private Sku sku;
}