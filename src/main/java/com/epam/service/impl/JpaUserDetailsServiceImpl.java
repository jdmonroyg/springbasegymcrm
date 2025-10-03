package com.epam.service.impl;


import com.epam.model.User;
import com.epam.repository.UserRepository;
import com.epam.service.LoginAttemptService;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jdmon on 29/09/2025
 * @project springbasegymcrm
 */

@Service
public class JpaUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final LoginAttemptService loginAttemptService;

    public JpaUserDetailsServiceImpl(UserRepository userRepository, LoginAttemptService loginAttemptService) {
        this.userRepository = userRepository;
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        if (loginAttemptService.isBlocked(username)){
            throw new LockedException("User account is locked due to too many failed in attempts");
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_"+ user.getRole().name()))
        );
    }
}
