package com.babacar.app.controller;

import com.babacar.app.dto.requests.NotificationRequest;
import com.babacar.app.dto.responses.ListResponse;
import com.babacar.app.dto.responses.NotificationResponse;
import com.babacar.app.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/templates")
@Tag(name = "Template APIs")
public class TemplateController {
    private final NotificationService notificationService;

    @PostMapping
    @Operation(summary = "create a template")
    public NotificationResponse create(
           @Valid @RequestBody NotificationRequest request){
        return notificationService.create(request);

    }

    @GetMapping("/{uuid}")
    @Operation(summary = "get a template by uuid")
    public NotificationResponse getByUuid(
            @PathVariable(value = "uuid") String uuid){
        return notificationService.getByUuid(uuid);
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "delete a template by uuid")
    public void delete(
            @PathVariable(value = "uuid") String uuid){
        notificationService.delete(uuid);

    }

    @GetMapping("/all")
    @Operation(summary = "get all templates with pagination")
    public ListResponse<?> findAll(
           @RequestParam(value = "page",defaultValue = "0") int page,
           @RequestParam(value = "size",defaultValue = "10") int size
    ){
        return notificationService.findAll(page,size);
    }
}
