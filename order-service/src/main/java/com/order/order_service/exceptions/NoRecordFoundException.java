package com.order.order_service.exceptions;

public class NoRecordFoundException extends RuntimeException{
    public NoRecordFoundException(){
        super("No record found!");
    }
}
