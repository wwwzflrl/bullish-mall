package com.bullish.mall.dto.response;

import com.bullish.mall.dto.DiscountDto;
import com.bullish.mall.dto.ProductDto;
import com.bullish.mall.entity.Discount;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductResponse {
  List<ProductDto> ProductList;

  List<DiscountDto> DiscountList;
}
