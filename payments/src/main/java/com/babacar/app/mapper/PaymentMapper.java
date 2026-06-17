package com.babacar.app.mapper;

import com.babacar.app.dto.responses.PaymentResponse;
import com.babacar.app.entities.Payments;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentResponse toPaymentResponse(Payments payments){
        return PaymentResponse.builder()
                .uuid(payments.getUuid())
                .invoiceUuid(payments.getInvoiceUuid())
                .amount(payments.getAmount())
                .paymentMethod(payments.getPaymentMethod())
                .dateTim(payments.getDateTime())
                .build();
    }

}
