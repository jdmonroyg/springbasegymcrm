package com.epam.controller;

import com.epam.dto.request.UpdateLoginRequestDto;
import com.epam.exception.GlobalExceptionHandler;
import com.epam.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author jdmon on 11/09/2025
 * @project springbasegymcrm
 */

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp()  {
        UserDetails userDetails = new User(
                "Jesus.Monroy",
                "irrelevant-password",
                List.of(new SimpleGrantedAuthority("ROLE_TRAINEE"))
        );

        var auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
                .defaultRequest(get("/")
                        .principal(auth))
                .build();
    }

    @Test
    @DisplayName("Login with good credentials")
    void loginSuccess() throws Exception {
        String token = UUID.randomUUID().toString();
        when(authService.login("user","pass"))
                .thenReturn(token);
        String body = """
                {
                "username":"user",
                "password": "pass"
                }
                """;
        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string(token));
    }

    @Test
    @DisplayName("Login with Bad Request 400")
    void loginWithBadPayload400() throws Exception {
        String badJson = "{}";

        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(badJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Login with bad credentials 401")
    void loginFailed401() throws Exception {
        when(authService.login("user","wrongPass"))
                .thenThrow(new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Invalid credentials"
                ));
        String body = """
                {
                "username":"user",
                "password": "wrongPass"
                }
                """;
        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Logout with token 204")
    void logoutSuccess() throws Exception {
        String token = getToken();
        doNothing().when(authService).logout(any(HttpServletRequest.class));
        mockMvc.perform(post("/auth/logout")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
        verify(authService, times(1)).logout(any(HttpServletRequest.class));
    }

    @Test
    @DisplayName("Change password with user authenticated 204")
    void changePasswordSuccess() throws Exception {
        String username = "Jesus.Monroy";

        doNothing().when(authService).changePassword(eq(username),
                any(UpdateLoginRequestDto.class));

        String body = """
            {
              "currentPassword": "A2sca04S8x",
              "newPassword": "iJm9N62fXa"
            }
            """;

        mockMvc.perform(put("/auth/password")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isNoContent());
    }

    private static String getToken() {
        return "Bearer " + UUID.randomUUID();
    }
}