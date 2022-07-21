package com.jonservices.bookservice.exceptions.handler;

import com.jonservices.bookservice.exceptions.ConversionProxyRequestException;
import com.jonservices.bookservice.exceptions.ExceptionResponse;
import com.jonservices.bookservice.exceptions.InvalidIDFormatException;
import com.jonservices.bookservice.exceptions.ResourceNotFoundException;
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

    @ExceptionHandler({InvalidIDFormatException.class, ConversionProxyRequestException.class})
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