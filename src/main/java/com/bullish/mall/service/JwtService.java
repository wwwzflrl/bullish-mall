package com.bullish.mall.service;

import com.bullish.mall.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface JwtService {
  String toToken(User user);

  Optional<String> getSubFromToken(String token);
}
