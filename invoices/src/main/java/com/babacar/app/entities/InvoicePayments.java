package com.babacar.app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "invoice_payments")
public class InvoicePayments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "invoice_uuid")
    private String invoiceUuid;
    @Column(name = "amount")
    private double amount;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "date")
    private LocalDateTime dateTime;
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoices invoices;
}

