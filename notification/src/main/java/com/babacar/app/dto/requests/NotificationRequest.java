package com.babacar.app.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record NotificationRequest(
    @NotBlank String name,
    @Email String email,
    @NotBlank String objet,
    @NotBlank String message
) {
}
