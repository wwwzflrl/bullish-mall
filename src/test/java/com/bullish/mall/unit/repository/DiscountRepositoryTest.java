package com.bullish.mall.unit.repository;

import com.bullish.mall.dto.DiscountConfigDto;
import com.bullish.mall.entity.Discount;
import com.bullish.mall.repository.DiscountRepository;
import com.bullish.mall.util.DiscountUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class DiscountRepositoryTest extends DbTestBase {

    @Autowired
    DiscountRepository discountRepository;

    @Test
    public void successToSaveReadJson() throws IOException {
        DiscountConfigDto config = DiscountUtil.createDiscountConfigDto(0);
        Discount discount = discountRepository.save(Discount.builder().config(config).build());
        Assertions.assertEquals(discount.getConfig(), config);
    }
}
