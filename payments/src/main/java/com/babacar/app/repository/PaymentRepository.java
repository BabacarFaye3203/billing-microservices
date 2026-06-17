package com.babacar.app.repository;

import com.babacar.app.entities.Payments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payments,Long> {


    Optional<Payments> findByUuid(String uuid);

    @Query("""
SELECT p from Payments p
""")
    Page<Payments> findAllPayments(Pageable pageable);
}
