package com.babacar.app.repository;

import com.babacar.app.entities.InvoiceProducts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceProductRepository extends JpaRepository<InvoiceProducts,Long> {


    Optional<InvoiceProducts> findByUuid(String uuid);
}
