package com.babacar.app.mapper;

import com.babacar.app.dto.responses.ClientResponse;
import com.babacar.app.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public ClientResponse mapToResponse(Client client) {
        return new ClientResponse(
                client.getUuid(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getPhone(),
                client.getAddress(),
                client.getCity(),
                client.getCountry(),
                client.getCreatedAt()
        );
    }
}
