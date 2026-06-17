package com.babacar.app.service;

import com.babacar.app.dto.responses.InvoicePaymentResponse;
import com.babacar.app.dto.responses.InvoiceResponse;
import com.babacar.app.entities.InvoicePayments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitmqProducer {

    private final RabbitTemplate rabbitTemplate;

    public static final Logger LOGGER= LoggerFactory.getLogger(RabbitmqProducer.class);

    public RabbitmqProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate=rabbitTemplate;
    }

    public void publish(String exchange, String routingKey, InvoicePaymentResponse invoicePayments){
        LOGGER.info("message to->{} sent",exchange);
        rabbitTemplate.convertAndSend(exchange,routingKey,invoicePayments);
    }
}
