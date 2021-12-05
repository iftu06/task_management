package com.task.management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ToDoError {
    GENERIC_ERROR_OCCURRED("E10001", "A generic error has occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    UNSUPPORTED_OPERATION("E10002", "This operation is not supported", HttpStatus.INTERNAL_SERVER_ERROR),


    UN_AUTHORIZED("E40001","The request could not be authorized.", HttpStatus.UNAUTHORIZED),
    RESOURCE_NOT_FOUND_ERROR("E40004","The requested resource could not be found.", HttpStatus.NOT_FOUND);


    private final String errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatusCode;

    ToDoError(String errorCode, String errorMessage, HttpStatus httpStatusCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatusCode = httpStatusCode;
    }
}
