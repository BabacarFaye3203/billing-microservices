package com.babacar.app.repository;

import com.babacar.app.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Products,Long> {

    Optional<Products> findByUuid(String uuid);

    @Query("""
    SELECT p from Products p
""")
    Page<Products> findAllProducts(Pageable pageable);
}
