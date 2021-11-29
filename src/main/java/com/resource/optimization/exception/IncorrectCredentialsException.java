package com.resource.optimization.exception;

import com.resource.optimization.exception.common.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectCredentialsException extends BadRequestException {

    public IncorrectCredentialsException(String message, Throwable ex) {
        super(message);
    }

    public IncorrectCredentialsException(String message) {
        super(message);
    }
}
