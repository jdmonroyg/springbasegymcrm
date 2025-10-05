package com.epam.exception;

/**
 * @author jdmon on 4/10/2025
 * @project springbasegymcrm
 */
public class TokenRevokedException extends RuntimeException {
    public TokenRevokedException(String message) {
        super(message);
    }
}
