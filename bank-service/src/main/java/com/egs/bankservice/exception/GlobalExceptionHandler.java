
package com.egs.bankservice.exception;

import com.egs.bankservice.model.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


@ControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Response<String>> unauthorizedException(UnauthorizedException  ex) {
        return new ResponseEntity<>(new Response<String>()
                .setStatus(HttpStatus.UNAUTHORIZED.value())
                .setMessage(ex.getMessage())
                .setError("UNAUTHORIZED"),
                HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response<String>> notFoundException(NotFoundException  ex) {
        return new ResponseEntity<>(new Response<String>()
                .setStatus(HttpStatus.NOT_FOUND.value())
                .setMessage(ex.getMessage())
                .setError("NOT_FOUND"),
                HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response<String>> notValidExceptionException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return new ResponseEntity<>(new Response<String>()
                .setStatus(HttpStatus.BAD_REQUEST.value())
                .setMessage(this.processFieldErrors(fieldErrors))
                .setError("Bad Request exception"),
                HttpStatus.BAD_REQUEST);

    }

    private String processFieldErrors(List<org.springframework.validation.FieldError> fieldErrors) {
        for (org.springframework.validation.FieldError fieldError: fieldErrors) {
          return fieldError.getDefaultMessage();
        }
        return null;
    }
}

