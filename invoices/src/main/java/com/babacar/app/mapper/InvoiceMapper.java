package com.babacar.app.mapper;

import com.babacar.app.dto.responses.ClientResponse;
import com.babacar.app.dto.responses.InvoiceProductResponse;
import com.babacar.app.dto.responses.InvoiceResponse;
import com.babacar.app.entities.InvoiceClients;
import com.babacar.app.entities.Invoices;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

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
}
