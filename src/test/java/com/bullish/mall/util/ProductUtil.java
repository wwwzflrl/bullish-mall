package com.bullish.mall.util;

import com.bullish.mall.dto.SkuDto;
import com.bullish.mall.dto.param.ProductParam;
import com.bullish.mall.entity.Product;
import com.bullish.mall.entity.Sku;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class ProductUtil {

    public static Product getDefaultProduct(Integer index) {
        Product product = Product.builder()
                .name("name" + index)
                .content("content" + index)
                .build();
        product.setSkuList(List.of(Sku.builder()
                .price(new BigDecimal(200 + index))
                .tags(Set.of("Tag" + index))
                .product(product)
                .build()
        ));
        return product;
    }

    public static ProductParam getDefaultProductParam(Integer index) {
        return ProductParam.builder()
                .name("name" + index)
                .content("content" + index)
                .skuList(List.of(SkuDto.builder()
                        .price(new BigDecimal(200 + index))
                        .tags(List.of("Tag" + index))
                        .build()
                ))
                .build();
    }
}
