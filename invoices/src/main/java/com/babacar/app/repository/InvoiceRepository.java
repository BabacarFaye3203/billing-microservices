package com.babacar.app.repository;

import com.babacar.app.entities.Invoices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoices,Long> {

    Optional<Invoices> findByUuid(String uuid);

    @Query("""
    SELECT i from Invoices i
""")
    Page<Invoices> getAllInvoices(Pageable pageable);

}
