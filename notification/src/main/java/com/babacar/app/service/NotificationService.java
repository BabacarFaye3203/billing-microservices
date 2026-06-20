package com.babacar.app.service;

import com.babacar.app.dto.requests.NotificationRequest;
import com.babacar.app.dto.responses.InvoiceResponse;
import com.babacar.app.dto.responses.ListResponse;
import com.babacar.app.dto.responses.NotificationResponse;
import com.babacar.app.entities.Notification;
import com.babacar.app.exception.NotificationNotFoundException;
import com.babacar.app.mapper.NotificationMapper;
import com.babacar.app.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public NotificationResponse create(NotificationRequest request){
        Notification template=Notification.builder()
                .uuid(UUID.randomUUID().toString())
                .name(request.name())
                .email(request.email())
                .objet(request.objet())
                .message(request.message())
                .date(LocalDate.now())
                .build();
        return notificationMapper.toTemplateResponse(template);
    }

    public NotificationResponse getByUuid(String uuid){
        Notification template=notificationRepository.findByUuid(uuid)
                .orElseThrow(
                        ()->new NotificationNotFoundException("template not found")
                );
        return notificationMapper.toTemplateResponse(template);
    }

    public void delete(String uuid){
        Notification template=notificationRepository.findByUuid(uuid)
                .orElseThrow(
                        ()->new NotificationNotFoundException("template not found")
                );
        notificationRepository.delete(template);
    }

    public ListResponse<?> findAll(
            int page,
            int size
    ){
        Pageable pageable= PageRequest.of(page,size);
        Page<Notification> templates=notificationRepository.findAllTemplates(pageable);
        List<NotificationResponse> content=notificationRepository.findAllTemplates(pageable)
                .stream()
                .map(notificationMapper::toTemplateResponse)
                .toList();
        return new ListResponse<NotificationResponse>(
                content,
                templates.getNumber(),
                content.size(),
                templates.getTotalElements(),
                templates.getTotalPages(),
                templates.hasNext()
        );

    }

    @KafkaListener(topics = "invoice-created",groupId = "notification")
    public void notify(InvoiceResponse request){

        Notification notification=Notification.builder()
                .uuid(UUID.randomUUID().toString())
                .name(request.client().firstName()+request.client().lastName())
                .email(request.client().email())
                .objet("bonjour")
                .message("message")
                .date(LocalDate.now())
                .build();
        notificationRepository.save(notification);

    }

    public ListResponse<?> getAllByEmail(
            String email,
            int page,
            int size
    ){
        Pageable pageable=PageRequest.of(page,size);
        Page<Notification> notifications=notificationRepository.findAllByEmail(email,pageable);
        List<NotificationResponse> content=notificationRepository.findAllByEmail(email,pageable)
                .stream()
                .map(notificationMapper::toTemplateResponse)
                .toList();

        return new ListResponse<NotificationResponse>(
                content,
                notifications.getNumber(),
                content.size(),
                notifications.getTotalElements(),
                notifications.getTotalPages(),
                notifications.hasNext()
        );
    }
}
