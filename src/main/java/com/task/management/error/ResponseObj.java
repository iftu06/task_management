package com.task.management.error;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class ResponseObj {

    private String message;

    private ReturnStatus returnStatus;

    private HttpStatus status;

    private Map responseBody;

    ResponseObj() {

    }

    public ResponseObj(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ReturnStatus getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(ReturnStatus returnStatus) {
        this.returnStatus = returnStatus;
    }

    public Map getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Map responseBody) {
        this.responseBody = responseBody;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
