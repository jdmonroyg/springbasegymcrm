package com.epam.service;

import java.time.Instant;

/**
 * @author jdmon on 4/10/2025
 * @project springbasegymcrm
 */
public interface TokenBlackListService {
    void blackListToken(String token, Instant expiration);
    boolean isBlackListed(String token);
}
