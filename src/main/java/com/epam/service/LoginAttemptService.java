package com.epam.service;

/**
 * @author jdmon on 1/10/2025
 * @project springbasegymcrm
 */
public interface LoginAttemptService {
    void loginSucceeded(String username);
    void loginFailed(String username);
    boolean isBlocked(String username);
}
