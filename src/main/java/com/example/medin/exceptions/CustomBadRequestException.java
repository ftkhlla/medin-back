package com.example.medin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomBadRequestException extends RuntimeException {

    public CustomBadRequestException() {
        super();
    }

    public CustomBadRequestException(String message) {
        super(message);
        System.err.println(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public CustomBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}

