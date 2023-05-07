package com.bullish.mall.dto.result;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConditionResult {
  private Boolean valid;

  private Integer count;
}
