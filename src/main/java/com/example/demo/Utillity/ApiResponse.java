package com.example.demo.Utillity;

import com.example.demo.error.ReturnStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ApiResponse {
    private HttpStatus httpStatus;
    private Object body;
    private ReturnStatus status;
    private String message;
}
