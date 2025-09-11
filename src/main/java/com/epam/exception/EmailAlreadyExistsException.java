package com.epam.exception;

/**
 * @author jdmon on 4/09/2025
 * @project springbasegymcrm
 */
public class EmailAlreadyExistsException extends RuntimeException{

    public EmailAlreadyExistsException() {
    }

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
