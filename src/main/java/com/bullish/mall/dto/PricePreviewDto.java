package com.bullish.mall.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PricePreviewDto {
  private PricePreviewDto preview;
  private List<PricePreviewDto> previewList;
}
