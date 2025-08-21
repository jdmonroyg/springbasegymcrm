package com.epam.config;

import com.epam.security.FavreBcryptPasswordEncoder;
import com.epam.security.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jdmon on 15/08/2025
 * @project springbasegymcrm
 */
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new FavreBcryptPasswordEncoder();
    }
}
