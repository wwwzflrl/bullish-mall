package com.bullish.mall.controller;

import com.bullish.mall.exception.InvalidAuthenticationException;
import com.bullish.mall.dto.param.LoginParam;
import com.bullish.mall.dto.response.UserWithToken;
import com.bullish.mall.service.JwtService;
import com.bullish.mall.entity.User;
import com.bullish.mall.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired private JwtService jwtService;

  @Autowired private UserRepository userRepository;

  @GetMapping("welcome")
  public ResponseEntity welcome() {
    return ResponseEntity.ok("You are welcome");
  }

  @GetMapping
  public ResponseEntity userInfo(@AuthenticationPrincipal User user) {
    return ResponseEntity.ok(user);
  }

  @PostMapping("/login")
  public ResponseEntity userLogin(@Valid @RequestBody LoginParam loginParam) {
    Optional<User> user = userRepository.findByUsername(loginParam.getUsername());
    if (user.isPresent()) {
      return ResponseEntity.ok(new UserWithToken(user.get(), jwtService.toToken(user.get())));
    } else {
      throw new InvalidAuthenticationException();
    }
  }
}
