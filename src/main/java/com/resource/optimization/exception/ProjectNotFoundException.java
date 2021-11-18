package com.resource.optimization.exception;

import com.resource.optimization.exception.common.NotFoundException;

public class ProjectNotFoundException extends NotFoundException {

    public ProjectNotFoundException(String message) {
        super(message);
    }
}
