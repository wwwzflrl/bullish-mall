package com.bullish.mall.api;

import com.bullish.mall.api.exception.InvalidAuthenticationException;
import com.bullish.mall.api.request.LoginBody;
import com.bullish.mall.api.response.UserWithToken;
import com.bullish.mall.api.security.JwtService;
import com.bullish.mall.core.user.User;
import com.bullish.mall.core.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserApi {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity userLogin(@Valid @RequestBody LoginBody loginBody) {
        Optional<User> user = userRepository.findByUsername(loginBody.getUsername());
        if (user.isPresent()) {
            return ResponseEntity.ok(new UserWithToken(user.get(), jwtService.toToken(user.get())));
        } else {
            throw new InvalidAuthenticationException();
        }
    }
}
