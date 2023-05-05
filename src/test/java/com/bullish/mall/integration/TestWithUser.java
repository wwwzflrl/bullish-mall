package com.bullish.mall.integration;

import com.bullish.mall.core.user.User;
import com.bullish.mall.core.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
abstract class TestWithUser {
    @Autowired
    protected UserRepository userRepository;

    protected void userFixture() {
        userRepository.deleteAll();
        userRepository.save(User.builder().admin(true).username("admin").build());
        userRepository.save(User.builder().admin(false).username("totti").build());
    }

    @BeforeEach
    public void setUp() throws Exception {
        userFixture();
    }
}
