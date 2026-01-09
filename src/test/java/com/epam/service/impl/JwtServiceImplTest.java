package com.epam.service.impl;

import com.epam.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jdmon on 8/01/2026
 * @project springbasegymcrm
 */
class JwtServiceImplTest {
    private JwtService jwtService;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        String secret = "mysecretkeydummymysecretkeydummymysecretkeydummy12";
        jwtService = new JwtServiceImpl(secret);

        userDetails = new User("jdoe", "password", Collections.emptyList());
    }

    @Test
    void shouldGenerateValidToken() {

        String token = jwtService.getToken(userDetails);

        assertNotNull(token);
        assertEquals("jdoe", jwtService.getUsername(token));
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void shouldReturnFalseForInvalidUser() {
        String token = jwtService.getToken(userDetails);

        UserDetails otherUser = new User("other", "password", Collections.emptyList());

        assertFalse(jwtService.isTokenValid(token, otherUser));
    }

    @Test
    void shouldReturnExpirationInFuture() {
        String token = jwtService.getToken(userDetails);

        Instant expiration = jwtService.getExpiration(token);

        assertTrue(expiration.isAfter(Instant.now()));
    }


}