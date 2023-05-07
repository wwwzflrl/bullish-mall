package com.bullish.mall.dto.result;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidResult {
  @Builder.Default Boolean price = true;
  @Builder.Default Boolean discount = true;
  @Builder.Default Boolean quantity = true;
}
