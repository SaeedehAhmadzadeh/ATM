package com.egs.atmservice.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
public class InternalServerException extends RuntimeException {
    public InternalServerException() {
    }

    public InternalServerException(String message) {
        super(message);
    }
}
