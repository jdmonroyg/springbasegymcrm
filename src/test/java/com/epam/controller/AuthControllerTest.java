package com.epam.controller;

import com.epam.dto.request.UpdateLoginRequestDto;
import com.epam.exception.GlobalExceptionHandler;
import com.epam.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
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
    @DisplayName("Login with Bad  401")
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

//    @Test
//    @DisplayName("Logout with token 204")
//    void logoutSuccess() throws Exception {
//        String token = getToken();
//        doNothing().when(authService).logout(token);
//        mockMvc.perform(post("/auth/logout")
//                .header("Authorization", token))
//                .andExpect(status().isNoContent());
//    }

    @Test
    @DisplayName("Logout without token 401")
    void logoutWithoutAuthorizationHeader400() throws Exception {
        mockMvc.perform(post("/auth/logout"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Change password with token 204")
    void changePasswordSuccess() throws Exception {
        String token = getToken();

        doNothing().when(authService).changePassword(token,
                new UpdateLoginRequestDto("Jesus.Monroy","A2sca04S8x","iJm9N62fXa"));

        String body = """
                {
                "username": "Jesus.Monroy",
                "currentPassword": "A2sca04S8x",
                "newPassword": "iJm9N62fXa"
                }
                """;

        mockMvc.perform(put("/auth/password")
                        .header("Authorization", token)
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isNoContent());
    }

    private static String getToken() {
        return "Bearer " + UUID.randomUUID();
    }
}