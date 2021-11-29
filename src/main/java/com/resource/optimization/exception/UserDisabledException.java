package com.resource.optimization.exception;

import com.resource.optimization.exception.common.ConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserDisabledException extends ConflictException {

    public UserDisabledException(String message) {
        super(message);
    }
}
