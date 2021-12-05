package com.task.management.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ToDoException extends RuntimeException {
    private ToDoError error;
    private String additionalErrorDetails;
}
