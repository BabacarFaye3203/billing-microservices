package com.babacar.app.dto.requests;

public record InvoiceProductRequest(
        String product_uuid,
        double quantity
) {}