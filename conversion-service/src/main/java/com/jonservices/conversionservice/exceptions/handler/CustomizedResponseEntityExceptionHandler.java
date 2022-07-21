package com.jonservices.conversionservice.exceptions.handler;

import com.jonservices.conversionservice.exceptions.ExceptionResponse;
import com.jonservices.conversionservice.exceptions.InvalidCurrencyFormatException;
import com.jonservices.conversionservice.exceptions.InvalidValueFormatException;
import com.jonservices.conversionservice.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exception, WebRequest request) {
        return createResponseEntity(exception, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({InvalidValueFormatException.class, InvalidCurrencyFormatException.class})
    public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception exception, WebRequest request) {
        return createResponseEntity(exception, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(Exception exception, WebRequest request) {
        return createResponseEntity(exception, request, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ExceptionResponse> createResponseEntity(Exception exception, WebRequest request, HttpStatus status) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, status);
    }
}

