package com.epam.exception;

/**
 * @author jdmon on 4/09/2025
 * @project springbasegymcrm
 */
public class InvalidCurrentPasswordException extends RuntimeException{

    public InvalidCurrentPasswordException() {
    }

    public InvalidCurrentPasswordException(String message) {
        super(message);
    }
}
