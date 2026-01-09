package com.epam.service.impl;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jdmon on 8/01/2026
 * @project springbasegymcrm
 */
class TokenBlackListServiceImplTest {

    @Test
    void shouldReturnTrueWhenTokenIsBlackListedAndNotExpired() {
        TokenBlackListServiceImpl service = new TokenBlackListServiceImpl();
        Instant future = Instant.now().plusSeconds(60);

        service.blackListToken("abc123", future);

        assertTrue(service.isBlackListed("abc123"));
    }

    @Test
    void shouldReturnFalseWhenTokenIsNotBlackListed() {
        TokenBlackListServiceImpl service = new TokenBlackListServiceImpl();

        assertFalse(service.isBlackListed("missingToken"));
    }

    @Test
    void shouldReturnFalseAndRemoveExpiredToken() {
        TokenBlackListServiceImpl service = new TokenBlackListServiceImpl();
        Instant past = Instant.now().minusSeconds(60);
        service.blackListToken("expiredToken", past);

        assertFalse(service.isBlackListed("expiredToken"));

        assertFalse(service.isBlackListed("expiredToken"));
    }

}