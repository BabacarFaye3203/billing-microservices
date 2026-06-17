package com.babacar.app.controller;

import com.babacar.app.dto.responses.ListResponse;
import com.babacar.app.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payments APIs")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/all")
    @Operation(summary = "get all pagination with pagination")
    public ListResponse<?> getAll(
           @RequestParam(value = "page",defaultValue = "0") int page,
           @RequestParam(value = "size",defaultValue = "10") int size
    ){
        return paymentService.getAll(page,size);
    }
}
