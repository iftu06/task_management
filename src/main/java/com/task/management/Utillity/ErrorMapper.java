package com.task.management.Utillity;

import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ErrorMapper {

    public static Map mapError(List<FieldError> errors) {

        return errors.stream()
                .collect(Collectors.toMap(fieldError ->
                        fieldError.getField(), fieldError -> fieldError.getDefaultMessage()));

    }
}
