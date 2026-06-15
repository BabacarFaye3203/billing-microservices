package com.babacar.app.repository;

import com.babacar.app.entities.Invoices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoices,Long> {

    Optional<Invoices> findByUuid(String uuid);
}
