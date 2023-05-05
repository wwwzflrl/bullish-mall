package com.bullish.mall.api.security;

import com.bullish.mall.core.user.User;
import com.bullish.mall.core.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepository;
    @Autowired private JwtService jwtService;
    private final String header = "Authorization";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        getTokenString(request.getHeader(header))
                .flatMap(token -> jwtService.getSubFromToken(token))
                .ifPresent(
                        username -> {
                            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                                userRepository
                                        .findByUsername(username)
                                        .ifPresent(
                                                user -> {
                                                    UsernamePasswordAuthenticationToken authenticationToken =
                                                            new UsernamePasswordAuthenticationToken(
                                                                    user, null, Collections.emptyList());
                                                    authenticationToken.setDetails(
                                                            new WebAuthenticationDetailsSource().buildDetails(request));
                                                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                                                });
                            }
                        });

        filterChain.doFilter(request, response);
    }

    private Optional<String> getTokenString(String header) {
        if (header == null) {
            return Optional.empty();
        } else {
            String[] split = header.split(" ");
            if (split.length < 2) {
                return Optional.empty();
            } else {
                return Optional.ofNullable(split[1]);
            }
        }
    }
}
