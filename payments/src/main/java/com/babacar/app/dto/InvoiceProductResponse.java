package com.babacar.app;

import lombok.Builder;

@Builder
public record InvoiceProductResponse(
        String uuid,
        String product_uuid,
        String name,
        double unitPrice,
        Long invoice_id,
        double quantity,
        double totalPrice
) {
}
