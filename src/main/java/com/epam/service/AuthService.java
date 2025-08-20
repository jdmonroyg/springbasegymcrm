package com.epam.service;

/**
 * @author jdmon on 16/08/2025
 * @project springbasegymcrm
 */
public interface AuthService {
    void authenticateTrainee(String username, String rawPassword);
    void authenticateTrainer(String username, String rawPassword);
}
