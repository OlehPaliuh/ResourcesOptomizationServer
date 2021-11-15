package com.resource.optimization.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectCredentialsException extends RuntimeException {

    public IncorrectCredentialsException(String message, Throwable ex) {
        super(message, ex);
    }

    public IncorrectCredentialsException(String message) {
        super(message);
    }
}
