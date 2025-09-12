package com.epam.controller;

import com.epam.service.AuthService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author jdmon on 11/09/2025
 * @project springbasegymcrm
 */

class AuthControllerTest {

    private MockMvc mockMvc;

    private AutoCloseable closeable;

    @Mock
    private AuthService authService;



    @BeforeEach
    void setUp()  {
        closeable = MockitoAnnotations.openMocks(this);
        AuthController authController = new AuthController(authService);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
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
    @DisplayName("Login with Bad Request")
    void loginWithBadPayload400() throws Exception {
        String badJson = "{}";

        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(badJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Login with Bad Credentials")
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
    @DisplayName("Logout without token")
    void logoutWithoutAuthorizationHeader400() throws Exception {
        mockMvc.perform(post("/auth/logout"))
                .andExpect(status().isBadRequest());
    }

}