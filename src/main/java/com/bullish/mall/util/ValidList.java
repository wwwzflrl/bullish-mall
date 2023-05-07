package com.bullish.mall.util;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidList<E> implements List<E> {
  @Valid @Delegate private List<E> list = new ArrayList<>();
}
