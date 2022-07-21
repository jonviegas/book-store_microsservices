package com.jonservices.conversionservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCurrencyFormatException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public InvalidCurrencyFormatException(String message) {
        super(message);
    }
}
