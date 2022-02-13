package com.egs.atmservice.exception;

import com.egs.atmservice.model.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response<String>> internalServerException(InternalServerException  ex) {
        return new ResponseEntity<>(new Response<String>()
                .setStatus(500)
                .setMessage(ex.getMessage())
                .setError("Internal Server Error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Response<String>> handleAllExceptions(Exception ex) {
        return new ResponseEntity<>(new Response<String>()
                .setStatus(500)
                .setMessage("Internal Server Error")
                .setError("Internal Server Error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response<String>> badRequestException(BadRequestException  ex) {
        return new ResponseEntity<>(new Response<String>()
                .setStatus(400)
                .setMessage(ex.getMessage())
                .setError("Bad Request exception"),
                HttpStatus.BAD_REQUEST);

    }
}
