package com.bullish.mall.integration;

import com.bullish.mall.dto.CartDiscountPreviewDto;
import com.bullish.mall.entity.BasketItem;
import com.bullish.mall.service.DiscountService;
import com.bullish.mall.service.rule.DiscountEnum;
import com.bullish.mall.util.BasketItemUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class DiscountServiceTest extends TestWithUser {

  @Autowired private DiscountService discountService;

  @Nested
  public class NullableValueTest {
    @Test
    public void invalidTypeAndNullValue() {

      CartDiscountPreviewDto previewDto =
          discountService.calcCartDiscountPreview(
              List.of(
                  BasketItemUtil.createMockData(
                      100L,
                      DiscountEnum.NONE.name(),
                      new BigDecimal(10),
                      new BigDecimal(20),
                      false,
                      new BigDecimal(100)),
                  BasketItemUtil.createMockData(null, null, null, null, null, null)));

      Assertions.assertEquals(previewDto.getDiscountAmount().intValue(), 0);
      Assertions.assertEquals(previewDto.getPayAmount().intValue(), 10000);
      Assertions.assertEquals(
          previewDto.getDiscountCalculateDtoList().get(1).getPayAmount().intValue(), 0);
    }
  }

  @Nested
  public class DefaultDiscountTest {

    @Test
    public void handleDefaultType() {

      CartDiscountPreviewDto previewDto =
          discountService.calcCartDiscountPreview(
              List.of(
                  BasketItemUtil.createMockData(
                      100L,
                      DiscountEnum.NONE.name(),
                      new BigDecimal(10),
                      new BigDecimal(20),
                      false,
                      new BigDecimal(100)),
                  BasketItemUtil.createMockData(
                      15L,
                      DiscountEnum.NONE.name(),
                      new BigDecimal(10.5),
                      new BigDecimal(20),
                      false,
                      new BigDecimal(3.2))));

      Assertions.assertEquals(previewDto.getQuantity(), 115L);
      Assertions.assertEquals(previewDto.getPayAmount().intValue(), 10048);
      Assertions.assertEquals(previewDto.getDiscountAmount().intValue(), 0);
    }
  }

  @Nested
  public class EveryNthDiscountTest {

    @Test
    public void handleNthType() {

      CartDiscountPreviewDto previewDto =
          discountService.calcCartDiscountPreview(
              List.of(
                  BasketItemUtil.createMockData(
                      100L,
                      DiscountEnum.EVERY_N_RATE.name(),
                      new BigDecimal(10),
                      new BigDecimal(0.2),
                      false,
                      new BigDecimal(20)),
                  BasketItemUtil.createMockData(
                      15L,
                      DiscountEnum.EVERY_N_RATE.name(),
                      new BigDecimal(30),
                      new BigDecimal(20),
                      false,
                      new BigDecimal(3.2))));

      Assertions.assertEquals(previewDto.getQuantity(), 115L);
      Assertions.assertEquals(previewDto.getPayAmount().intValue(), 2008);
      Assertions.assertEquals(previewDto.getDiscountAmount().intValue(), 40);
    }
  }

  @Nested
  public class FullPieceDiscountTest {

    @Test
    public void handleFullPieceType() {

      CartDiscountPreviewDto previewDto =
          discountService.calcCartDiscountPreview(
              List.of(
                  BasketItemUtil.createMockData(
                      20L,
                      DiscountEnum.FULL_PIECE.name(),
                      new BigDecimal(100),
                      new BigDecimal(0.2),
                      false,
                      new BigDecimal(20)),
                  BasketItemUtil.createMockData(
                      30L,
                      DiscountEnum.FULL_PIECE.name(),
                      new BigDecimal(30),
                      new BigDecimal(20),
                      false,
                      new BigDecimal(3.2))));

      Assertions.assertEquals(previewDto.getQuantity(), 50L);
      Assertions.assertEquals(previewDto.getPayAmount().intValue(), 475);
      Assertions.assertEquals(previewDto.getDiscountAmount().intValue(), 20);
    }
  }

  @Nested
  public class FullAmountDiscountTest {

    @Test
    public void handleFullPieceType() {

      CartDiscountPreviewDto previewDto =
          discountService.calcCartDiscountPreview(
              List.of(
                  BasketItemUtil.createMockData(
                      20L,
                      DiscountEnum.FULL_PIECE.name(),
                      new BigDecimal(500),
                      new BigDecimal(0.2),
                      false,
                      new BigDecimal(20)),
                  BasketItemUtil.createMockData(
                      30L,
                      DiscountEnum.FULL_PIECE.name(),
                      new BigDecimal(30),
                      new BigDecimal(10),
                      false,
                      new BigDecimal(30))));

      Assertions.assertEquals(previewDto.getQuantity(), 50L);
      Assertions.assertEquals(previewDto.getPayAmount().intValue(), 1289);
      Assertions.assertEquals(previewDto.getDiscountAmount().intValue(), 10);
    }
  }
}
