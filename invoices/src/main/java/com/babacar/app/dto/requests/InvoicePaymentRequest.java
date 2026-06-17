package com.babacar.app.dto.requests;

import java.time.LocalDateTime;

public record InvoicePaymentRequest(
        double amount,
        String paymentMethod,
        LocalDateTime dateTime,
        int invoice
) {
}
