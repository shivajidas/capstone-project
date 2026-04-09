package com.order.order_service.controllers.advice;

import com.order.order_service.exceptions.ApplicationUnavailableException;
import com.order.order_service.exceptions.NoRecordFoundException;
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

    @ResponseBody
    @ExceptionHandler(ApplicationUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    String applicationUnavailableException(ApplicationUnavailableException aue){
        return aue.getMessage();
    }

}
