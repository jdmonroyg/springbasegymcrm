package com.epam.filter;

import com.epam.exception.TokenRevokedException;
import com.epam.service.JwtService;
import com.epam.service.TokenBlackListService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author jdmon on 8/01/2026
 * @project springbasegymcrm
 */
class JwtAuthenticationFilterTest {
    private JwtService jwtService;
    private UserDetailsService userDetailsService;
    private TokenBlackListService blackListService;
    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        jwtService = mock(JwtService.class);
        userDetailsService = mock(UserDetailsService.class);
        blackListService = mock(TokenBlackListService.class);
        filter = new JwtAuthenticationFilter(jwtService, userDetailsService, blackListService);
    }

    @Test
    void shouldAuthenticateValidToken() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer validtoken");
        when(blackListService.isBlackListed("validtoken")).thenReturn(false);
        when(jwtService.getUsername("validtoken")).thenReturn("jdoe");
        when(userDetailsService.loadUserByUsername("jdoe"))
                .thenReturn(new User("jdoe", "password", Collections.emptyList()));
        when(jwtService.isTokenValid(eq("validtoken"), any())).thenReturn(true);

        assertDoesNotThrow(() -> filter.doFilterInternal(request, response, chain));

        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void shouldThrowExceptionForRevokedToken() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer badtoken");
        when(blackListService.isBlackListed("badtoken")).thenReturn(true);

        org.junit.jupiter.api.Assertions.assertThrows(TokenRevokedException.class,
                () -> filter.doFilterInternal(request, response, chain));
    }

}