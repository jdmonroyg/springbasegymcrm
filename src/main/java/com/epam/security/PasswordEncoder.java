package com.epam.security;

/**
 * @author jdmon on 15/08/2025
 * @project springbasegymcrm
 */
public interface PasswordEncoder {
    String encode(CharSequence rawPassword);
    boolean matches(CharSequence rawPassword, String encodePassword);
}
