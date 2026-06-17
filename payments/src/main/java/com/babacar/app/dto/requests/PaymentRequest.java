package com.babacar.app.dto.requests;

import java.time.LocalDateTime;

public record PaymentRequest(
        String invoiceUuid,
        double amount,
        String paymentMethod,
        LocalDateTime dateTime
) {
}
