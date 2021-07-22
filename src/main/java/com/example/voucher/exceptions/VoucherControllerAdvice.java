package com.example.voucher.exceptions;

import lombok.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class VoucherControllerAdvice {

    @ExceptionHandler(VoucherNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    ApiError handleVoucherNotFoundException(VoucherNotFoundException exception) {
        return new ApiError(exception.getMessage());
    }

    @ExceptionHandler(VoucherValidationException.class)
    @ResponseStatus(BAD_REQUEST)
    ApiError handleVoucherValidationException(VoucherValidationException exception) {
        return new ApiError(exception.getMessage());
    }
}

@Value
class ApiError {
    String message;
}
