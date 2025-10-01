package com.epam.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author jdmon on 29/09/2025
 * @project springbasegymcrm
 */
public interface JwtService {
    String getToken(UserDetails userDetails);
    String getUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);

}
