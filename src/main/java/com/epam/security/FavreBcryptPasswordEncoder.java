package com.epam.security;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * @author jdmon on 15/08/2025
 * @project springbasegymcrm
 */
public class FavreBcryptPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        int cost = 12;
        return BCrypt.withDefaults().hashToString(cost, rawPassword.toString().toCharArray());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodePassword) {
        return BCrypt.verifyer()
                .verify(rawPassword.toString().toCharArray(), encodePassword)
                .verified;
    }
}
