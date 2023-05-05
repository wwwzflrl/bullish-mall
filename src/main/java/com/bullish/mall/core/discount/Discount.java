package com.bullish.mall.core.discount;

import com.bullish.mall.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Discount  extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String chargeConfig;

    private String ruleConfig;

    private String qualifyConfig;
}