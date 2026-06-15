package com.babacar.app.mapper;

import com.babacar.app.dto.responses.NotificationResponse;
import com.babacar.app.entities.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public NotificationResponse toTemplateResponse(Notification t){
        return new NotificationResponse(
                t.getUuid(),
                t.getName(),
                t.getEmail(),
                t.getObjet(),
                t.getMessage(),
                t.getDate()
        );
    }
}
