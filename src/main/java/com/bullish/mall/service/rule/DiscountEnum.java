package com.bullish.mall.service.rule;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiscountEnum {
  NONE("none"),

  /** Total Amount reach X, minus Y * */
  FULL_AMOUNT("full_amount"),

  /** Total quantity reach X, minus Y * */
  FULL_PIECE("full_piece"),

  /** Every X item, direct have Y percent */
  EVERY_N_RATE("every_n_rate");

  private String type;
}
