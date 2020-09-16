package com.bitcoindemo.controlleradvice;

import java.io.IOException;
import com.bitcoindemo.exception.ExceptionResponse;
import com.bitcoindemo.exception.PriceNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleDatetimeFormatException(final MethodArgumentTypeMismatchException exception, final WebRequest request) {
        final ExceptionResponse ex = new ExceptionResponse();
        ex.setErrorMessage(exception.getMessage());
        ex.setErrorCode(HttpStatus.BAD_REQUEST.value());
        return ex;
    }

    @ExceptionHandler(PriceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ExceptionResponse handlePriceNotFoundException(final PriceNotFoundException exception, final WebRequest request) {
        final ExceptionResponse ex = new ExceptionResponse();
        ex.setErrorMessage(exception.getMessage());
        ex.setErrorCode(HttpStatus.NOT_FOUND.value());
        return ex;
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(value = HttpStatus.GATEWAY_TIMEOUT)
    public @ResponseBody ExceptionResponse handleIOException(final IOException exception, final WebRequest request) {
        final ExceptionResponse ex = new ExceptionResponse();
        ex.setErrorMessage(exception.getMessage());
        ex.setErrorCode(HttpStatus.GATEWAY_TIMEOUT.value());
        return ex;
    }

}