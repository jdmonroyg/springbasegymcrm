package com.epam.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jdmon on 8/01/2026
 * @project springbasegymcrm
 */
class LoginAttemptServiceImplTest {

    @Test
    void shouldIncrementAttemptsAndBlockAfterMax() {
        LoginAttemptServiceImpl service = new LoginAttemptServiceImpl();

        service.loginFailed("jdoe");
        assertFalse(service.isBlocked("jdoe"));

        service.loginFailed("jdoe");
        assertFalse(service.isBlocked("jdoe"));

        service.loginFailed("jdoe");
        assertTrue(service.isBlocked("jdoe")); // blocked after 3 attempts
    }

    @Test
    void shouldClearAttemptsOnSuccess() {
        LoginAttemptServiceImpl service = new LoginAttemptServiceImpl();

        service.loginFailed("jdoe");
        service.loginFailed("jdoe");
        service.loginFailed("jdoe");
        assertTrue(service.isBlocked("jdoe"));

        service.loginSucceeded("jdoe");
        assertFalse(service.isBlocked("jdoe")); // cleared after success
    }

}