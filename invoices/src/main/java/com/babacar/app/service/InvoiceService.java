package com.babacar.app.service;

import com.babacar.app.constants.InvoiceStatus;
import com.babacar.app.constants.PaymentMethodConstants;
import com.babacar.app.dto.requests.InvoiceRequest;
import com.babacar.app.dto.responses.*;
import com.babacar.app.entities.InvoiceClients;
import com.babacar.app.entities.InvoicePayments;
import com.babacar.app.entities.InvoiceProducts;
import com.babacar.app.entities.Invoices;
import com.babacar.app.exception.InvoiceNotFountException;
import com.babacar.app.exception.InvoicePaymentMethodNotAllowedException;
import com.babacar.app.exception.InvoiceStatusNotAllowedException;
import com.babacar.app.mapper.InvoiceMapper;
import com.babacar.app.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;
    @Value("${rabbitmq.routingKey.name}")
    private String routingKey;

    private final InvoiceRepository invoiceRepository;
    private final RestTemplate restTemplate;
    private final KafkaProducer kafkaProducer;
    private final InvoiceMapper invoiceMapper;
    private final RabbitmqProducer rabbitmqProducer;
    private final WebClient.Builder webClient;

    InvoicePaymentResponse invoicePaymentResponse=InvoicePaymentResponse.builder().build();

    @Transactional
    public InvoiceResponse create(InvoiceRequest request){
        Invoices invoice=new Invoices();
        invoice.setUuid(UUID.randomUUID().toString());
        invoice.setNumber("In"+UUID.randomUUID());
        if (!InvoiceStatus.VALID_STATUS.contains(request.status()))
            throw new  InvoiceStatusNotAllowedException("invoice status not allowed");
        if (!PaymentMethodConstants.VALID_PAYMENTS.contains(request.paymentMethod()))
            throw new InvoicePaymentMethodNotAllowedException("invoice payment method not allowed");
        invoice.setStatus(request.status());

        List<InvoiceProducts> invoiceProducts=request.invoiceProduct()
                .stream()
                .map(ipr->{
//                    ProductResponse productResponse=restTemplate.getForObject(
//                            "http://BILLING-PRODUCTS/api/v1/products/"+ipr.product_uuid(),
//                            ProductResponse.class
//                    );
                    ProductResponse productResponse=webClient.build()
                            .get()
                            .uri("http://BILLING-PRODUCTS/api/v1/products/"+ipr.product_uuid())
                            .retrieve()
                            .bodyToMono(ProductResponse.class)
                            .block();
                    assert productResponse != null;
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

//        ClientResponse clientResponse=restTemplate.getForObject(
//                "http://CLIENTS/api/v1/clients/"+request.client_uuid(),
//                ClientResponse.class
//        );

        ClientResponse clientResponse=webClient.build()
                .get()
                .uri("http://CLIENTS/api/v1/clients/"+request.client_uuid())
                .retrieve()
                .bodyToMono(ClientResponse.class)
                .block();
        assert clientResponse != null;
        InvoiceClients invoiceClient=invoiceMapper.mapToInvoiceClient(clientResponse);
        invoice.setClients(invoiceClient);

        List<InvoicePayments> invoicePayments=request.paymentRequests()
                .stream()
                .map(invoicePaymentRequest ->{
                    InvoicePayments savedIp=InvoicePayments.builder()
                        .invoiceUuid(invoice.getUuid())
                        .amount(invoicePaymentRequest.amount())
                        .paymentMethod(invoicePaymentRequest.paymentMethod())
                        .dateTime(LocalDateTime.now())
                        .invoices(invoice)
                        .uuid(UUID.randomUUID().toString())
                        .build();

                    invoicePaymentResponse=invoiceMapper.mapToInvoicePaymentResponse(savedIp);

                    return savedIp;

                }
                ).collect(Collectors.toList());
        rabbitmqProducer.publish(exchangeName,routingKey,invoicePaymentResponse);

        invoice.setPayments(invoicePayments);

        invoice.setInvoiceProducts(invoiceProducts);
        invoice.setPayments(invoicePayments);
        Invoices saved=invoiceRepository.save(invoice);

        List<InvoicePaymentResponse> invoicePaymentResponses=invoiceMapper
                .mapToInvoicePaymentResponseList(saved);
        List<InvoiceProductResponse> invoiceProductResponses=invoiceMapper
                .mapToInvoiceProductResponseList(saved);
        ClientResponse clientResponse1=invoiceMapper.mapToInvoiceClientResponse(saved);

        InvoiceResponse response= new InvoiceResponse(
                saved.getUuid(),
                saved.getNumber(),
                invoiceProductResponses,
                saved.getPrice(),
                saved.getStatus(),
                clientResponse1,
                invoicePaymentResponses
        );

        kafkaProducer.publish(response);

        return response;

    }

    public InvoiceResponse getByUuid(String uuid){
        Invoices invoices=invoiceRepository.findByUuid(uuid).orElseThrow(
                ()->new InvoiceNotFountException("invoice not found")
        );

        List<InvoicePaymentResponse> invoicePaymentResponses=invoiceMapper
                .mapToInvoicePaymentResponseList(invoices);
        List<InvoiceProductResponse> invoiceProductResponses=invoiceMapper
                .mapToInvoiceProductResponseList(invoices);
        ClientResponse clientResponse=invoiceMapper.mapToInvoiceClientResponse(invoices);

        return new InvoiceResponse(
                invoices.getUuid(),
                invoices.getNumber(),
                invoiceProductResponses,
                invoices.getPrice(),
                invoices.getStatus(),
                clientResponse,
                invoicePaymentResponses
        );
    }

    public void delete(String uuid){
        Invoices invoices=invoiceRepository.findByUuid(uuid).orElseThrow(
                ()->new InvoiceNotFountException("invoice not foun")
        );
        invoiceRepository.delete(invoices);
    }

    public ListResponse<?> getAll(
            int page,
            int size
    ){
        Pageable pageable= PageRequest.of(page,size);

        Page<Invoices> invoices=invoiceRepository.getAllInvoices(pageable);
        List<InvoiceResponse> content=invoiceRepository.getAllInvoices(pageable)
                .stream()
                .map(invoiceMapper::mapToInvoiceResponse)
                .toList();

        return new ListResponse<InvoiceResponse>(
                content,
                invoices.getNumber(),
                content.size(),
                invoices.getTotalElements(),
                invoices.getTotalPages(),
                invoices.hasNext()
        );
    }


}
