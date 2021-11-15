package com.resource.optimization.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message, Throwable ex) {
        super(message, ex);
    }

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
