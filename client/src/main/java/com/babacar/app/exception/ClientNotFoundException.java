package com.babacar.app.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;


public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(String message){
        super(message);
    }
}
