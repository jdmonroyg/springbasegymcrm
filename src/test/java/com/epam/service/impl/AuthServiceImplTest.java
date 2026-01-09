package com.epam.service.impl;

import com.epam.dto.request.UpdateLoginRequestDto;
import com.epam.exception.InvalidCurrentPasswordException;
import com.epam.exception.NotFoundException;
import com.epam.model.Trainee;
import com.epam.repository.UserRepository;
import com.epam.service.JwtService;
import com.epam.service.TokenBlackListService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author jdmon on 8/01/2026
 * @project springbasegymcrm
 */
class AuthServiceImplTest {

    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private TokenBlackListService bl;
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        encoder = mock(PasswordEncoder.class);
        authenticationManager = mock(AuthenticationManager.class);
        jwtService = mock(JwtService.class);
        bl = mock(TokenBlackListService.class);
        authService = new AuthServiceImpl(userRepository, encoder, authenticationManager, jwtService, bl);
    }

    @Test
    void loginShouldReturnTokenForValidCredentials() {
        UserDetails userDetails = mock(UserDetails.class);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, "password", Collections.emptyList());

        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(jwtService.getToken(any(UserDetails.class))).thenReturn("jwt-token");

        String token = authService.login("jdoe", "password");

        assertEquals("jwt-token", token);

    }

    @Test
    void logoutShouldBlacklistToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer abc123");
        when(jwtService.getExpiration("abc123")).thenReturn(Instant.now().plusSeconds(3600));

        authService.logout(request);

        verify(bl, times(1)).blackListToken(eq("abc123"), any(Instant.class));
    }

    @Test
    void changePasswordShouldUpdatePassword() {
        // Arrange: usamos Trainee como implementación concreta de User
        Trainee user = new Trainee(
                "John", "Doe", "john.doe@email.com",
                "jdoe", "oldpass", true,
                LocalDate.of(1990, 1, 1), "123 Street"
        );

        when(userRepository.findByUsername("jdoe")).thenReturn(Optional.of(user));
        when(encoder.matches("oldpass", "oldpass")).thenReturn(true);
        when(encoder.encode("newpass")).thenReturn("encodedNewPass");

        UpdateLoginRequestDto dto = new UpdateLoginRequestDto("oldpass", "newpass");

        // Act
        authService.changePassword("jdoe", dto);

        // Assert
        assertEquals("encodedNewPass", user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void changePasswordShouldThrowWhenCurrentPasswordInvalid() {
        // Arrange: Trainee como implementación concreta
        Trainee user = new Trainee(
                "Jane", "Doe", "jane.doe@email.com",
                "jdoe", "storedPass", true,
                LocalDate.of(1992, 5, 10), "456 Avenue"
        );

        when(userRepository.findByUsername("jdoe")).thenReturn(Optional.of(user));
        when(encoder.matches("wrongpass", "storedPass")).thenReturn(false);

        UpdateLoginRequestDto dto = new UpdateLoginRequestDto("wrongpass", "newpass");

        assertThrows(InvalidCurrentPasswordException.class,
                () -> authService.changePassword("jdoe", dto));
    }

    @Test
    void changePasswordShouldThrowWhenUserNotFound() {
        when(userRepository.findByUsername("jdoe")).thenReturn(Optional.empty());

        UpdateLoginRequestDto dto = new UpdateLoginRequestDto("oldpass", "newpass");

        assertThrows(NotFoundException.class, () -> authService.changePassword("jdoe", dto));
    }


}