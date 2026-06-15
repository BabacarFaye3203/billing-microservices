package com.babacar.app.dto.requests;

import java.util.List;

public record InvoiceRequest(
        List<InvoiceProductRequest> invoiceProduct,
        String status,
        String client_uuid
) {
}
