package com.epam.service.impl;

import com.epam.dto.request.UpdateLoginRequestDto;
import com.epam.exception.InvalidCurrentPasswordException;
import com.epam.exception.InvalidSessionException;
import com.epam.exception.NotFoundException;
import com.epam.exception.UnauthorizedException;
import com.epam.model.User;
import com.epam.repository.UserRepository;
import com.epam.security.PasswordEncoder;
import com.epam.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author jdmon on 16/08/2025
 * @project springbasegymcrm
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final Map<String, String> tokenStore = new HashMap<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    @Transactional(readOnly = true)
    public String login(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UnauthorizedException("Unauthorized User: bad credentials"));
        Boolean matches = encoder.matches(rawPassword, user.getPassword());
        LOGGER.debug("Authentication result for user '{}': {}", username, matches);
        if (!matches) {
            LOGGER.warn("User authentication failed: bad credentials");
            throw new UnauthorizedException("Unauthorized user: invalid credentials");
        }
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, user.getEmail());
        return token;
    }

    @Override
    public void logout(String token){
        token= normalizeToken(token);
        if (tokenStore.remove(token)!=null) {
            LOGGER.debug("The token:{} was deleted", token);
            LOGGER.info("The logout was successful");
        } else {
            LOGGER.warn("Logout attempt with non-existent or already removed token: {}", token.substring(0, 6));
            throw new InvalidSessionException("Session already terminated or token invalid");
        }
    }

    @Override
    @Transactional
    public void changePassword(String token, UpdateLoginRequestDto dto) {
        LOGGER.info("validating token");
        validateAuthentication(token);
        User user = userRepository.findByUsername(dto.username())
                .orElseThrow(() ->
                        new NotFoundException("User was not found"));
        if (!encoder.matches(dto.currentPassword(), user.getPassword())) {
            LOGGER.warn("Current password does not match for user: {}", dto.username());
            throw new InvalidCurrentPasswordException("The current password is incorrect");
        }
        user.setPassword(encoder.encode(dto.newPassword()));
        LOGGER.info("The password was updated successfully");
        userRepository.save(user);
    }

    @Override
    public void validateAuthentication(String token) {
        if (!isAuthenticated(token)){
            LOGGER.warn("The token is invalid");
            throw new UnauthorizedException("User is not authenticated");
        }
    }

    private boolean isAuthenticated(String token) {
        return tokenStore.containsKey(normalizeToken(token));
    }

    private String normalizeToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }
}
