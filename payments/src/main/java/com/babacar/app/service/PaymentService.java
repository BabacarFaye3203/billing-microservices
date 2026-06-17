package com.babacar.app.service;

import com.babacar.app.dto.requests.PaymentRequest;
import com.babacar.app.dto.responses.InvoicePaymentResponse;
import com.babacar.app.dto.responses.ListResponse;
import com.babacar.app.dto.responses.PaymentResponse;
import com.babacar.app.entities.Payments;
import com.babacar.app.mapper.PaymentMapper;
import com.babacar.app.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private static final Logger LOGGER= LoggerFactory.getLogger(PaymentService.class);

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void create(InvoicePaymentResponse paymentResponse){

        LOGGER.info("payment->{} successfully consumed ",paymentResponse.uuid());

        Payments payment=Payments.builder()
                .uuid(UUID.randomUUID().toString())
                .invoiceUuid(paymentResponse.invoiceUuid())
                .amount(paymentResponse.amount())
                .paymentMethod(paymentResponse.paymentMethod())
                .dateTime(LocalDateTime.now())
                .build();
        paymentRepository.save(payment);



    }

    public ListResponse<?> getAll(
            int page,
            int size
    ){
        Pageable pageable= PageRequest.of(page,size);
        Page<Payments> payments=paymentRepository.findAllPayments(pageable);

        List<PaymentResponse> content=paymentRepository.findAllPayments(pageable)
                .stream()
                .map(paymentMapper::toPaymentResponse)
                .toList();

        return new ListResponse<PaymentResponse>(
                content,
                payments.getNumber(),
                content.size(),
                payments.getTotalElements(),
                payments.getTotalPages(),
                payments.hasNext()
        );

    }
}
