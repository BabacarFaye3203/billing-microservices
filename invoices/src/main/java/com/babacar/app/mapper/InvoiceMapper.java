package com.babacar.app.mapper;

import com.babacar.app.dto.responses.ClientResponse;
import com.babacar.app.dto.responses.InvoicePaymentResponse;
import com.babacar.app.dto.responses.InvoiceProductResponse;
import com.babacar.app.dto.responses.InvoiceResponse;
import com.babacar.app.entities.InvoiceClients;
import com.babacar.app.entities.InvoicePayments;
import com.babacar.app.entities.Invoices;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class InvoiceMapper {
    public InvoiceClients mapToInvoiceClient(ClientResponse clientResponse){
        return InvoiceClients.builder()
                .uuid(UUID.randomUUID().toString())
                .clientUuid(clientResponse.uuid())
                .firstName(clientResponse.firstName())
                .lastName(clientResponse.lastName())
                .email(clientResponse.email())
                .phone(clientResponse.phone())
                .address(clientResponse.address())
                .city(clientResponse.city())
                .country(clientResponse.country())
                .createdAt(clientResponse.createdAt())
                .build();

    }

    public List<InvoiceProductResponse> mapToInvoiceProductResponseList(Invoices invoices){
        return invoices.getInvoiceProducts().stream()
                .map(invoiceProducts1 -> InvoiceProductResponse.builder()
                        .uuid(invoiceProducts1.getUuid())
                        .product_uuid(invoiceProducts1.getProduct_uuid())
                        .name(invoiceProducts1.getName()
                        ).unitPrice(invoiceProducts1.getUnitPrice())
                        .invoice_id(invoiceProducts1.getInvoices().getId())
                        .quantity(invoiceProducts1.getQuantity())
                        .totalPrice(invoiceProducts1.getTotalPrice()).build())
                .collect(Collectors.toList());
    }

    public ClientResponse mapToInvoiceClientResponse(Invoices invoices){
        return new ClientResponse(
                invoices.getClients().getClientUuid(),
                invoices.getClients().getFirstName(),
                invoices.getClients().getLastName(),
                invoices.getClients().getEmail(),
                invoices.getClients().getPhone(),
                invoices.getClients().getAddress(),
                invoices.getClients().getCity(),
                invoices.getClients().getCountry(),
                invoices.getClients().getCreatedAt()
        );
    }

    public InvoicePaymentResponse mapToInvoicePaymentResponse(InvoicePayments invoicePayments){
        return InvoicePaymentResponse.builder()
                .uuid(invoicePayments.getUuid())
                .invoiceUuid(invoicePayments.getInvoiceUuid())
                .amount(invoicePayments.getAmount())
                .paymentMethod(invoicePayments.getPaymentMethod())
                .dateTime(invoicePayments.getDateTime())
                .build();
    }

    public List<InvoicePaymentResponse> mapToInvoicePaymentResponseList(Invoices invoices){
        return invoices.getPayments().stream()
                .map(invoicePayments -> InvoicePaymentResponse.builder()
                        .uuid(invoicePayments.getUuid())
                        .invoiceUuid(invoicePayments.getInvoiceUuid())
                        .amount(invoicePayments.getAmount())
                        .paymentMethod(invoicePayments.getPaymentMethod())
                        .dateTime(invoicePayments.getDateTime())
                        .build())
                .collect(Collectors.toList());
    }

    public InvoiceResponse mapToInvoiceResponse(Invoices invoice){
        return new InvoiceResponse(
                invoice.getUuid(),
                invoice.getNumber(),
                mapToInvoiceProductResponseList(invoice),
                invoice.getPrice(),
                invoice.getStatus(),
                mapToClientResponse(invoice),
                mapToInvoicePaymentResponseList(invoice)
        );
    }


    public ClientResponse mapToClientResponse(Invoices invoice){
        return new ClientResponse(
                invoice.getClients().getUuid(),
                invoice.getClients().getFirstName(),
                invoice.getClients().getLastName(),
                invoice.getClients().getEmail(),
                invoice.getClients().getPhone(),
                invoice.getClients().getAddress(),
                invoice.getClients().getCity(),
                invoice.getClients().getCountry(),
                invoice.getClients().getCreatedAt()
        );
    }

}
