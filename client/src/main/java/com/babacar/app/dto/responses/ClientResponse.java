package com.babacar.app.dto.responses;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record ClientResponse(
        String uuid,
        String firstName,
        String lastName,
        String email,
        String phone,
        String address,
        String city,
        String country,
        LocalDate createdAt

) {
}