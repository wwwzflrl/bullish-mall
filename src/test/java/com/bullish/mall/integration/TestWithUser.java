package com.bullish.mall.integration;

import com.bullish.mall.api.security.JwtService;
import com.bullish.mall.core.user.User;
import com.bullish.mall.core.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
abstract class TestWithUser {
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected JwtService jwtService;

    protected String adminToken;

    protected String userToken;

    protected void userFixture() {
        userRepository.deleteAll();
        User admin = userRepository.save(User.builder().admin(true).username("admin").build());
        User user = userRepository.save(User.builder().admin(false).username("totti").build());
        adminToken = jwtService.toToken(admin);
        userToken = jwtService.toToken(user);
    }

    @BeforeEach
    public void setUp() throws Exception {
        userFixture();
    }
}
