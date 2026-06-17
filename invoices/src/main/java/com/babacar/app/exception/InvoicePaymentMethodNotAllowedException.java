package com.babacar.app.exception;

public class InvoicePaymentMethodNotAllowedException extends RuntimeException{
    public InvoicePaymentMethodNotAllowedException(String message){
        super(message);
    }

}
