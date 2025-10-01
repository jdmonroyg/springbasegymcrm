package com.epam.controller;

import com.epam.dto.request.LoginRequestDto;
import com.epam.dto.request.UpdateLoginRequestDto;
import com.epam.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @author jdmon on 4/09/2025
 * @project springbasegymcrm
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user and generate access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful, " +
                    "token returned in response body"),
            @ApiResponse(responseCode = "400", description = "Invalid request: one or more required fields " +
                    "are missing or contain invalid values"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password"),
    })
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        LOGGER.info("The login is starting");
        String token = authService.login(loginRequestDto.username(), loginRequestDto.password());
        LOGGER.info("The login was successfully");
        return ResponseEntity.ok(token);
    }

//    @PostMapping("/logout")
//    @Operation(summary = "Logout user")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "Logout successful, token invalidated"),
//            @ApiResponse(responseCode = "401", description = "Invalid or expired authentication token")
//    })
//    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token){
//        LOGGER.info("The logout is starting");
//        authService.logout(token);
//        return ResponseEntity.noContent().build();
//    }

    @PutMapping("/password")
    @Operation(summary = "Update user password")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request: the current password is incorrect " +
                    "or one or more required fields are missing or contain invalid values"),
            @ApiResponse(responseCode = "401", description = "Invalid or missing authentication token"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal UserDetails user,
                                    @RequestBody @Valid UpdateLoginRequestDto updateLoginDto){
        LOGGER.info("The change password is starting");
        authService.changePassword(user.getUsername(), updateLoginDto);
        return ResponseEntity.noContent().build();
    }

}
