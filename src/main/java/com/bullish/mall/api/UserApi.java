package com.bullish.mall.api;

import com.bullish.mall.api.exception.InvalidAuthenticationException;
import com.bullish.mall.api.request.LoginDto;
import com.bullish.mall.api.response.UserWithToken;
import com.bullish.mall.api.security.JwtService;
import com.bullish.mall.core.user.User;
import com.bullish.mall.core.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserApi {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity userInfo(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity userLogin(@Valid @RequestBody LoginDto loginDto) {
        Optional<User> user = userRepository.findByUsername(loginDto.getUsername());
        if (user.isPresent()) {
            return ResponseEntity.ok(new UserWithToken(user.get(), jwtService.toToken(user.get())));
        } else {
            throw new InvalidAuthenticationException();
        }
    }
}
