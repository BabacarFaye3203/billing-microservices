package com.babacar.app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "invoice_products")
public class InvoiceProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "product_uuid")
    private String product_uuid;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    Invoices invoices;
    @Column(name = "unit_price")
    private double unitPrice;
    @Column(name = "quantity")
    private double quantity;
    @Column(name = "total_price")
    private double totalPrice;
}
