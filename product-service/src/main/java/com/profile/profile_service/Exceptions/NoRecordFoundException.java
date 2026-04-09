package com.profile.profile_service.Exceptions;

public class NoRecordFoundException extends RuntimeException{
    public NoRecordFoundException(){
        super("No record found!");
    }
}
