package com.bullish.mall.dto.result;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Getter
public class DiscountResult {
  private List<ValidResult> validResultList = new ArrayList<>();

  private List<String> reasons = new ArrayList<>();

  private List<DiscountItemResult> discountItemResults = new ArrayList<>();

  private BigDecimal originalAmount = new BigDecimal(0).setScale(2);

  private BigDecimal actualAmount = new BigDecimal(0).setScale(2);

  private BigDecimal discountReduce = new BigDecimal(0).setScale(2);

  public void addValidResult(ValidResult validResult, String reason) {
    validResultList.add(validResult);
    reasons.add(reason);
  }

  public Boolean isValid(Predicate<ValidResult> predicate) {
    return this.validResultList.stream().allMatch(predicate);
  }

  public BigDecimal calculateOriginalAmount() {
    originalAmount =
        discountItemResults.stream()
            .map((o) -> o.getOriginalPrice())
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .setScale(2);
    return originalAmount;
  }

  public void calculateMetrics() {
    calculateOriginalAmount();
    actualAmount =
        discountItemResults.stream()
            .map((o) -> o.getCurrentPrice())
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .setScale(2);
    discountReduce = originalAmount.subtract(actualAmount).setScale(2);
  }
}
