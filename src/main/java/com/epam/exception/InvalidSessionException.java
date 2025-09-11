package com.epam.exception;

/**
 * @author jdmon on 4/09/2025
 * @project springbasegymcrm
 */
public class InvalidSessionException extends RuntimeException{

    public InvalidSessionException() {
    }

    public InvalidSessionException(String message) {
        super(message);
    }
}
