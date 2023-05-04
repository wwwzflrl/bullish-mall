package com.bullish.mall.api.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@JsonRootName("user")
@AllArgsConstructor
public class LoginBody {
    @NotBlank(message = "can't be empty")
    private String username;
}
