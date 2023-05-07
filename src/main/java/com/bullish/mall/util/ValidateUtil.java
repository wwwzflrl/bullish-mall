package com.bullish.mall.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidateUtil {
  public static <T> List<String> valid(T t) {
    Validator validatorFactory = Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<T>> errors = validatorFactory.validate(t);
    return errors.stream().map(error -> error.getMessage()).collect(Collectors.toList());
  }
}
