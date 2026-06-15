package com.babacar.app.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ApiResponse(
        String message,
        int code,
        HttpStatus httpStatus,
        LocalDateTime dateTime
) {
}
