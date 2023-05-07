package com.bullish.mall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(
    ignoreUnknown = true,
    value = {"BasketItemList"})
public class Product extends BaseEntity {
  @Id @GeneratedValue private Long id;

  private String name;

  private String content;

  @Builder.Default private Boolean deleted = Boolean.FALSE;

  @Setter
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
  private List<Sku> skuList;
}
