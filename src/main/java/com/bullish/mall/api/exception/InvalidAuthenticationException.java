package com.bullish.mall.api.exception;

public class InvalidAuthenticationException extends RuntimeException {
    public InvalidAuthenticationException() {
        super("Invalid User Name");
    }
}
