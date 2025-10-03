package com.epam.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author jdmon on 5/09/2025
 * @project springbasegymcrm
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DataError>> handleException(MethodArgumentNotValidException e) {
        LOGGER.warn("MethodArgumentNotValid exception: {}", e.getMessage());
        List<DataError> errors = e.getFieldErrors().stream().map(DataError::new).toList();
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleException(UnauthorizedException e){
        LOGGER.warn("Unauthorized exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleException(NotFoundException e){
        LOGGER.warn("NotFoundException exception: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleException(EmailAlreadyExistsException e) {
        LOGGER.warn("EmailAlreadyExistsException exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<String> handleException(MissingRequestHeaderException e){
        LOGGER.warn("Authentication token is missing: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler({ BadCredentialsException.class, UsernameNotFoundException.class })
    public ResponseEntity<String> handleException(AuthenticationException e) {
        LOGGER.warn("Authentication failed {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("error bad credentials");
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<String> handleLocked(LockedException e) {
        LOGGER.warn(e.getMessage());
        return ResponseEntity.status(423).body(e.getMessage());
    }

    @ExceptionHandler(InvalidCurrentPasswordException.class)
    public ResponseEntity<String> handleException(InvalidCurrentPasswordException e) {
        LOGGER.warn("Invalid Current Password: {}", e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(InactiveUserException.class)
    public ResponseEntity<String> handleException(InactiveUserException e) {
        LOGGER.warn("Inactive user exception: {}", e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }



    public record DataError(String field, String error) {
        public DataError(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

}
