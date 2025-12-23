package com.epam.exception;

/**
 * @author jdmon on 4/09/2025
 * @project springbasegymcrm
 */
public class DownstreamServiceException extends RuntimeException{

    public DownstreamServiceException() {
    }

    public DownstreamServiceException(String message) {
        super(message);
    }
}
