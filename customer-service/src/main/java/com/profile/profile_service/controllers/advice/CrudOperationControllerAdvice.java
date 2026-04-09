package com.profile.profile_service.controllers.advice;

import com.profile.profile_service.Exceptions.NoRecordFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CrudOperationControllerAdvice {

    @ResponseBody
    @ExceptionHandler(NoRecordFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String noRecordFoundException(NoRecordFoundException nrfe){
        return nrfe.getMessage();
    }

}
