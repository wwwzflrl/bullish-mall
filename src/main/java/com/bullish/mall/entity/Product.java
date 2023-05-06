package com.bullish.mall.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
public class Product extends BaseEntity {
  @Id @GeneratedValue private Long id;

  private String name;

  private String content;

  @Setter
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
  private List<Sku> skuList;
}
