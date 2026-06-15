package com.babacar.app.dto.responses;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ApiResponse(
        String message,
        HttpStatus status,
        long code
) {
}
