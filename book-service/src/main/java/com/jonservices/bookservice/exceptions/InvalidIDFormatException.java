package com.jonservices.bookservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidIDFormatException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public InvalidIDFormatException(String message) {
        super(message);
    }
}
