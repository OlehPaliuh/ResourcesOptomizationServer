package com.resource.optimization.exception;

import com.resource.optimization.exception.common.BadRequestException;
import com.resource.optimization.exception.common.ConflictException;
import com.resource.optimization.exception.common.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(com.resource.optimization.exception.common.NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<Object> handleUserNotFoundException(NotFoundException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("status", NOT_FOUND.value());

        return new ResponseEntity<>(body, NOT_FOUND);
    }

    @ExceptionHandler(com.resource.optimization.exception.common.ConflictException.class)
    @ResponseStatus(CONFLICT)
    public ResponseEntity<Object> handleUserNotFoundException(ConflictException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("status", CONFLICT.value());

        return new ResponseEntity<>(body, CONFLICT);
    }

    @ExceptionHandler(com.resource.optimization.exception.common.BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<Object> handleUserNotFoundException(BadRequestException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("status", BAD_REQUEST.value());

        return new ResponseEntity<>(body, BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResponseEntity<Object> handleIncorrectCredentialsException(
            IncorrectCredentialsException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
