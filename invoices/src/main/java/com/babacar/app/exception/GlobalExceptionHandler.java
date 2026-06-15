package com.babacar.app.exception;

import com.babacar.app.dto.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvoiceStatusNotAllowedException.class)
    public ApiResponse handleInvoiceStatusNotAllowed(InvoiceStatusNotAllowedException e){
        return new ApiResponse(
                e.getMessage(),
                HttpStatus.NOT_ACCEPTABLE,
                HttpStatus.NOT_ACCEPTABLE.value()
        );

    }
}
