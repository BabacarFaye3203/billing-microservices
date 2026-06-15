package com.babacar.app.service;

import com.babacar.app.dto.responses.InvoiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, InvoiceResponse> kafkaTemplate;

    public void publish(InvoiceResponse invoiceResponse){
       kafkaTemplate.send("invoice-created",invoiceResponse);
    }
}
