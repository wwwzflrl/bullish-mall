package com.bullish.mall.entity;

import com.bullish.mall.dto.DiscountConfigDto;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@EqualsAndHashCode(callSuper = true)
@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
public class Discount extends BaseEntity {
  @Id @GeneratedValue private Long id;

  private String description;

  @Builder.Default private Boolean deleted = Boolean.FALSE;

  @Type(JsonType.class)
  @Column(nullable = false, columnDefinition = "json")
  private DiscountConfigDto config;
}
