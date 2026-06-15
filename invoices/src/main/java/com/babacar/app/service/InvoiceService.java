package com.babacar.app.service;

import com.babacar.app.InvoiceStatus;
import com.babacar.app.dto.requests.InvoiceRequest;
import com.babacar.app.dto.responses.ClientResponse;
import com.babacar.app.dto.responses.InvoiceProductResponse;
import com.babacar.app.dto.responses.InvoiceResponse;
import com.babacar.app.dto.responses.ProductResponse;
import com.babacar.app.entities.InvoiceClients;
import com.babacar.app.entities.InvoiceProducts;
import com.babacar.app.entities.Invoices;
import com.babacar.app.exception.InvoiceStatusNotAllowedException;
import com.babacar.app.mapper.InvoiceMapper;
import com.babacar.app.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final RestTemplate restTemplate;
    private final KafkaProducer kafkaProducer;
    private final InvoiceMapper invoiceMapper;

    @Transactional
    public InvoiceResponse create(InvoiceRequest request){
        Invoices invoice=new Invoices();
        invoice.setUuid(UUID.randomUUID().toString());
        invoice.setNumber("In"+UUID.randomUUID());
        if (!InvoiceStatus.VALID_STATUS.contains(request.status()))
            throw new  InvoiceStatusNotAllowedException("invoice status not allowed");
        invoice.setStatus(request.status());

        List<InvoiceProducts> invoiceProducts=request.invoiceProduct()
                .stream()
                .map(ipr->{
                    ProductResponse productResponse=restTemplate.getForObject(
                            "http://localhost:8080/api/v1/products/"+ipr.product_uuid(),
                            ProductResponse.class
                    );
                    return InvoiceProducts.builder()
                            .uuid(UUID.randomUUID().toString())
                            .product_uuid(productResponse.uuid())
                            .name(productResponse.name())
                            .invoices(invoice)
                            .unitPrice(productResponse.price())
                            .quantity(ipr.quantity())
                            .totalPrice(ipr.quantity()* productResponse.price())
                            .build();
                }).collect(Collectors.toList());

        ClientResponse clientResponse=restTemplate.getForObject(
                "http://localhost:8083/api/v1/clients/"+request.client_uuid(),
                ClientResponse.class
        );
        InvoiceClients invoiceClient=invoiceMapper.mapToInvoiceClient(clientResponse);
        invoice.setClients(invoiceClient);

        invoice.setInvoiceProducts(invoiceProducts);
        Invoices saved=invoiceRepository.save(invoice);

        List<InvoiceProductResponse> invoiceProductResponses=invoiceMapper.mapToInvoiceProductResponseList(saved);
        ClientResponse clientResponse1=invoiceMapper.mapToInvoiceClientResponse(saved);


        InvoiceResponse response= new InvoiceResponse(
                saved.getUuid(),
                saved.getNumber(),
                invoiceProductResponses,
                saved.getPrice(),
                saved.getStatus(),
                clientResponse1
        );

        kafkaProducer.publish(response);

        return response;




    }
}
