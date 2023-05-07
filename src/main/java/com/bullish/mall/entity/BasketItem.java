package com.bullish.mall.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
public class BasketItem extends BaseEntity {
  @Id @GeneratedValue private Long id;

  private Long quantity;

  @NotNull @ManyToOne private Product product;

  @NotNull @ManyToOne private Sku sku;

  @ManyToOne private Discount discount;

  @NotNull @ManyToOne private User user;
}
