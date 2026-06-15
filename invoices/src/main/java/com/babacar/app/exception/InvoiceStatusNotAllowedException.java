package com.babacar.app.exception;

public class InvoiceStatusNotAllowedException extends RuntimeException{
    public InvoiceStatusNotAllowedException(String message){
        super(message);
    }
}
