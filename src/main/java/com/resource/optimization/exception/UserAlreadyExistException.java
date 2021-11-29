package com.resource.optimization.exception;

import com.resource.optimization.exception.common.ConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistException extends ConflictException {
    public UserAlreadyExistException(String message, Throwable ex) {
        super(message);
    }

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
