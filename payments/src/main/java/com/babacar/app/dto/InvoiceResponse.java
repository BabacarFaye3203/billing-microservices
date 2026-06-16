package com.babacar.app;

import lombok.Builder;

import java.util.List;

@Builder
public record InvoiceResponse(
        String uuid,
        String number,
        List<InvoiceProductResponse> invoiceProducts,
        double price,
        String status,
        ClientResponse client
) {}
