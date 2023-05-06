package com.bullish.mall.exception;

public class InvalidAuthenticationException extends RuntimeException {
  public InvalidAuthenticationException() {
    super("Invalid User Name");
  }
}
