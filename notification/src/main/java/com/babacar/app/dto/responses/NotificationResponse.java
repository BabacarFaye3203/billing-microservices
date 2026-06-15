package com.babacar.app.dto.responses;

import java.time.LocalDate;

public record NotificationResponse(
        String uuid,
        String name,
        String email,
        String objet,
        String message,
        LocalDate date
) {
}
