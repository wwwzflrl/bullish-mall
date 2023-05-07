package com.bullish.mall.integration;

import com.bullish.mall.service.JwtService;
import com.bullish.mall.entity.User;
import com.bullish.mall.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
abstract class TestWithUser {
  @Autowired protected UserRepository userRepository;

  @Autowired protected JwtService jwtService;

  protected String adminToken;

  protected User admin;

  protected String userToken;

  protected User user;

  protected void userFixture() {
    userRepository.deleteAll();
    admin = userRepository.save(User.builder().admin(true).username("admin").build());
    user = userRepository.save(User.builder().admin(false).username("totti").build());
    adminToken = jwtService.toToken(admin);
    userToken = jwtService.toToken(user);
  }

  @BeforeEach
  public void setUp() throws Exception {
    userFixture();
  }
}
