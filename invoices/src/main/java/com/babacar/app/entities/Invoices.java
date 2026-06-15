package com.babacar.app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "invoices")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Invoices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "number")
    private String number;
    @OneToMany(mappedBy = "invoices",cascade = CascadeType.ALL)
    private List<InvoiceProducts> invoiceProducts;
    @Column(name = "price")
    private double price;
    @Column(name = "status")
    private String status;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private InvoiceClients clients;

}
