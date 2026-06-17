package com.babacar.app.dto.responses;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record PaymentResponse(
        String uuid,
        String invoiceUuid,
        double amount,
        String paymentMethod,
        LocalDateTime dateTim
) {
}
