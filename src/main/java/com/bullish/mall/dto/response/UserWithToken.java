package com.bullish.mall.dto.response;

import com.bullish.mall.entity.User;
import lombok.Getter;

@Getter
public class UserWithToken {
  private Long id;
  private String username;
  private String token;

  public UserWithToken(User user, String token) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.token = token;
  }
}
