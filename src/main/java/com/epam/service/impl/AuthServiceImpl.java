package com.epam.service.impl;

import com.epam.dto.request.UpdateLoginRequestDto;
import com.epam.exception.InvalidCurrentPasswordException;
import com.epam.exception.NotFoundException;
import com.epam.model.User;
import com.epam.repository.UserRepository;
import com.epam.service.AuthService;
import com.epam.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jdmon on 16/08/2025
 * @project springbasegymcrm
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder encoder,
                           AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional(readOnly = true)
    public String login(String username, String rawPassword) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, rawPassword));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        LOGGER.debug("Authentication result for user '{}' was ok", username);
        return jwtService.getToken(userDetails);
    }

    @Override
    @Transactional
    public void changePassword(String username, UpdateLoginRequestDto dto) {
        LOGGER.info("changing password for user authenticated");
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new NotFoundException("User was not found"));
        if (!encoder.matches(dto.currentPassword(), user.getPassword())) {
            LOGGER.warn("Current password does not match for user: {}", username);
            throw new InvalidCurrentPasswordException("The current password is incorrect");
        }
        user.setPassword(encoder.encode(dto.newPassword()));
        LOGGER.info("The password was updated successfully");
        userRepository.save(user);
    }

}
