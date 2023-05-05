package com.bullish.mall.api.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@AllArgsConstructor
public class FieldErrorResource {
    private String resource;
    private String field;
    private String code;
    private String message;
}
