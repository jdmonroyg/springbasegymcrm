package com.epam.service;

import com.epam.dto.request.UpdateLoginRequestDto;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author jdmon on 16/08/2025
 * @project springbasegymcrm
 */
public interface AuthService {
    String login(String username, String rawPassword);
    void logout(HttpServletRequest token);
    void changePassword(String username, UpdateLoginRequestDto updateLoginDto);
}
