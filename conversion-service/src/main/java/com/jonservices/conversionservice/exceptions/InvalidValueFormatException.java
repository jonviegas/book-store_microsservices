package com.jonservices.conversionservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidValueFormatException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public InvalidValueFormatException(String message) {
        super(message);
    }
}

