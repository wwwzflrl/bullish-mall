package com.bullish.mall.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWithToken {
  private Long id;
  private String username;
  private String token;
}
