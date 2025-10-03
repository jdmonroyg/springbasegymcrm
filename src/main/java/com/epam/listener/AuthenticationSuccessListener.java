package com.epam.listener;

import com.epam.service.LoginAttemptService;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author jdmon on 1/10/2025
 * @project springbasegymcrm
 */
@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final LoginAttemptService loginAttemptService;

    public AuthenticationSuccessListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails){
            loginAttemptService.loginSucceeded(userDetails.getUsername());
        }

    }
}
