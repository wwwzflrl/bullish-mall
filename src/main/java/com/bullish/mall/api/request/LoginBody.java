package com.bullish.mall.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginBody {
    @NotBlank(message = "can't be empty")
    private String username;
}
