package com.task.management.exception;

public class ToDoExceptionFactory {


    public ToDoExceptionFactory() {
    }

    public static ToDoException genericError(String errorMessage) {
        return new ToDoException(ToDoError.GENERIC_ERROR_OCCURRED, errorMessage);
    }


    public static final ToDoException RESOURCE_NOT_FOUND_ERROR =
            new ToDoException(ToDoError.RESOURCE_NOT_FOUND_ERROR, "");

    public static final ToDoException UNSUPPORTED_OPERATION =
            new ToDoException(ToDoError.UNSUPPORTED_OPERATION, "");



}
