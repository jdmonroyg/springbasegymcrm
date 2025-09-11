package com.epam.service;

import com.epam.dto.request.UpdateLoginDto;

/**
 * @author jdmon on 16/08/2025
 * @project springbasegymcrm
 */
public interface AuthService {
    String login(String username, String rawPassword);
    void logout(String token);
    void changePassword(String token, UpdateLoginDto updateLoginDto);
    void validateAuthentication(String token);
}
