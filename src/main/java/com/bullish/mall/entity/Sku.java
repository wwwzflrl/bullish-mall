package com.bullish.mall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
public class Sku extends BaseEntity {
  @Id @GeneratedValue private Long id;

  @Column(precision = 10, scale = 2)
  private BigDecimal price;

  @Type(JsonType.class)
  @Column(nullable = false, columnDefinition = "json")
  private Set<String> tags;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(nullable = false)
  private Product product;
}
