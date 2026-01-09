package com.epam.service.impl;

import com.epam.model.Role;
import com.epam.model.Trainee;
import com.epam.repository.UserRepository;
import com.epam.service.LoginAttemptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author jdmon on 8/01/2026
 * @project springbasegymcrm
 */
class JpaUserDetailsServiceImplTest {

    private UserRepository userRepository;
    private LoginAttemptService loginAttemptService;
    private JpaUserDetailsServiceImpl service;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        loginAttemptService = mock(LoginAttemptService.class);
        service = new JpaUserDetailsServiceImpl(userRepository, loginAttemptService);
    }

    @Test
    void shouldThrowLockedExceptionWhenUserIsBlocked() {
        when(loginAttemptService.isBlocked("jdoe")).thenReturn(true);

        assertThrows(LockedException.class, () -> service.loadUserByUsername("jdoe"));
    }

    @Test
    void shouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
        when(loginAttemptService.isBlocked("jdoe")).thenReturn(false);
        when(userRepository.findByUsername("jdoe")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("jdoe"));
    }

    @Test
    void shouldReturnUserDetailsWhenUserExistsAndNotBlocked() {
        Trainee user = new Trainee(
                "John", "Doe", "john.doe@email.com",
                "jdoe", "encodedPass", true,
                LocalDate.of(1990, 1, 1), "123 Street"
        );
        user.setRole(Role.TRAINEE);

        when(loginAttemptService.isBlocked("jdoe")).thenReturn(false);
        when(userRepository.findByUsername("jdoe")).thenReturn(Optional.of(user));

        UserDetails userDetails = service.loadUserByUsername("jdoe");

        assertEquals("jdoe", userDetails.getUsername());
        assertEquals("encodedPass", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_TRAINEE")));
    }

}