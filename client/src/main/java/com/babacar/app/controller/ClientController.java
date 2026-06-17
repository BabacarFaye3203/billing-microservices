package com.babacar.app.controller;

import com.babacar.app.dto.ListResponse;
import com.babacar.app.dto.request.ClientRequest;
import com.babacar.app.dto.responses.ClientResponse;
import com.babacar.app.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponse> createClient(
            @Valid @RequestBody ClientRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.createClient(request));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ClientResponse> getClientByUuid(
            @PathVariable(name = "uuid") String uuid) {

        return ResponseEntity.ok(
                clientService.getClientByUuid(uuid)
        );
    }

    @GetMapping
    public ResponseEntity<ListResponse<?>> getAllClients(int page, int size) {

        return ResponseEntity.ok(
                clientService.getAllClients(page,size)
        );
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ClientResponse> updateClient(
            @PathVariable(name = "uuid") String uuid,
            @Valid @RequestBody ClientRequest request) {

        return ResponseEntity.ok(
                clientService.updateClient(uuid, request)
        );
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteClient(
            @PathVariable(name = "uuid") String uuid) {

        clientService.deleteClient(uuid);

        return ResponseEntity.noContent().build();
    }
}