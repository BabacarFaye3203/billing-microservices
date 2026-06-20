package com.babacar.app.exception;

import com.babacar.app.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@ControllerAdvice
public class HandleGlobalException {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseBody
    public ApiResponse handleProductNotFoundException(ProductNotFoundException exception){
        return new ApiResponse(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND
//                LocalDateTime.now()
        );
    }
}
