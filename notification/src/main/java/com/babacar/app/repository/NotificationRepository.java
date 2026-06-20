package com.babacar.app.repository;

import com.babacar.app.entities.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    Optional<Notification> findByUuid(String uuid);

        @Query("""
        SELECT t from Notification t
    """)
    Page<Notification> findAllTemplates(Pageable pageable);

        Page<Notification> findAllByEmail(String email,Pageable pageable);
}
