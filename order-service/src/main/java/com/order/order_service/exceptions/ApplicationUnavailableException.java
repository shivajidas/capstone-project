package com.order.order_service.exceptions;

import org.springframework.http.HttpStatusCode;

public class ApplicationUnavailableException extends RuntimeException {

    private final HttpStatusCode statusCode;
    public ApplicationUnavailableException(String message, HttpStatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
