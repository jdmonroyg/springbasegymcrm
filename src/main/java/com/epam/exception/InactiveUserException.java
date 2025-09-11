package com.epam.exception;

/**
 * @author jdmon on 4/09/2025
 * @project springbasegymcrm
 */
public class InactiveUserException extends RuntimeException{

    public InactiveUserException() {
    }

    public InactiveUserException(String message) {
        super(message);
    }
}
