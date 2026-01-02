package com.varol.WellPass_Mananagement_System.dtos.response.common;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private Boolean success = false;

    private String message;

    private String errorCode;

    private List<String> errors;

    private LocalDateTime timestamp;

    public ErrorResponse(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String message, String errorCode, List<String> errors) {
        this.message = message;
        this.errorCode = errorCode;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }
}






