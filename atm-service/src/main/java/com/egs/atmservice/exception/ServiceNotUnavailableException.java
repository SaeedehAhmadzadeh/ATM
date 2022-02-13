package com.egs.atmservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceNotUnavailableException extends RuntimeException {
    public ServiceNotUnavailableException(String message) {
        super(message);
    }
}
