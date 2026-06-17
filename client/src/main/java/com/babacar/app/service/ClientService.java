package com.babacar.app.service;

import com.babacar.app.dto.ListResponse;
import com.babacar.app.dto.request.ClientRequest;
import com.babacar.app.dto.responses.ClientResponse;
import com.babacar.app.entities.Client;
import com.babacar.app.exception.ClientNotFoundException;
import com.babacar.app.mapper.ClientMapper;
import com.babacar.app.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {


    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;


    public ClientResponse createClient(ClientRequest request) {

        Client client = Client.builder()
                .uuid(UUID.randomUUID().toString())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phone(request.phone())
                .address(request.address())
                .city(request.city())
                .country(request.country())
                .createdAt(LocalDate.now())
                .build();

        client = clientRepository.save(client);

        return clientMapper.mapToResponse(client);
    }

    public ClientResponse getClientByUuid(String uuid) {

        Client client = clientRepository.findByUuid(uuid)
                .orElseThrow(() -> new ClientNotFoundException("Client introuvable"));

        return clientMapper.mapToResponse(client);
    }


    public ListResponse<?> getAllClients(int page, int size) {

        Pageable pageable= PageRequest.of(page,size);
        Page<Client> clients= clientRepository.findAllClients(pageable);
        List<ClientResponse> content=clientRepository.findAllClients(pageable)
                .stream()
                .map(clientMapper::mapToResponse)
                .toList();

        return new  ListResponse<ClientResponse>(
                content,
                clients.getNumber(),
                content.size(),
                clients.getTotalElements(),
                clients.getTotalPages(),
                clients.hasNext()
                );
    }

    public ClientResponse updateClient(String uuid, ClientRequest request) {

        Client client = clientRepository.findByUuid(uuid)
                .orElseThrow(() -> new ClientNotFoundException("Client introuvable"));

        client.setFirstName(request.firstName());
        client.setLastName(request.lastName());
        client.setEmail(request.email());
        client.setPhone(request.phone());
        client.setAddress(request.address());
        client.setCity(request.city());
        client.setCountry(request.country());

        client = clientRepository.save(client);

        return clientMapper.mapToResponse(client);
    }


    public void deleteClient(String uuid) {

        Client client = clientRepository.findByUuid(uuid)
                .orElseThrow(() -> new ClientNotFoundException("Client introuvable"));

        clientRepository.delete(client);
    }

}

