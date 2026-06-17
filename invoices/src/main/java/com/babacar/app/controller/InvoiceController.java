package com.babacar.app.controller;

import com.babacar.app.dto.requests.InvoiceRequest;
import com.babacar.app.dto.responses.InvoiceResponse;
import com.babacar.app.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
@Tag(name = "Invoices APIs")
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PostMapping
    @Operation(summary = "creation of a invoice")
    public InvoiceResponse create(
            @RequestBody InvoiceRequest request
    ){
        return invoiceService.create(request);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "get by uuid")
    public InvoiceResponse getByUuid(
            @PathVariable(name = "uuid") String uuid){
        return invoiceService.getByUuid(uuid);
    }

    @DeleteMapping("/{uuid}")
    public void delete(
            @PathVariable(name = "uuid") String uuid){
        invoiceService.delete(uuid);
    }

}
