package com.jdbcservlets.service.impl;

import com.jdbcservlets.data.dao.ClientDao;
import com.jdbcservlets.data.dao.impl.ClientDaoImpl;
import com.jdbcservlets.data.domain.Client;
import com.jdbcservlets.dto.ClientCreateDto;
import com.jdbcservlets.dto.ClientResponseDto;
import com.jdbcservlets.service.ClientService;
import com.jdbcservlets.service.GenerationService;
import com.jdbcservlets.service.exceptions.ClientNotFoundException;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ClientServiceImpl implements ClientService {

    private static final String COMPANY_NAME = "google";

    private final ClientDao clientDao = new ClientDaoImpl();
    private final GenerationService generationService = new GenerationServiceImpl();

    @Override
    public ClientResponseDto create(ClientCreateDto createDto) {
        String password = generationService.generatePassword();
        String email = createDto.getFirstName().toLowerCase()
                + "."
                + createDto.getLastName().toLowerCase()
                + "@"
                + COMPANY_NAME
                + ".com";

        Client client = Client.builder()
                .firstName(createDto.getFirstName())
                .lastName(createDto.getLastName())
                .password(password)
                .email(email)
                .phoneNumber(createDto.getPhoneNumber())
                .build();

        Client savedClient = clientDao.save(client);

        return getClientResponseDto(savedClient);
    }

    @Override
    public ClientResponseDto findById(Long id) {
        return getClientResponseDto(this.getClientById(id));
    }

    @Override
    public List<ClientResponseDto> findAll() {
        return clientDao.findAll()
                .stream()
                .map(this::getClientResponseDto)
                .collect(toList());
    }

    @Override
    public void deleteById(Long id) {
        clientDao.delete(this.getClientById(id));
    }

    private Client getClientById(Long id) {
        return clientDao.findById(id).orElseThrow(() -> new ClientNotFoundException("Client not found!"));
    }

    private ClientResponseDto getClientResponseDto(Client savedClient) {
        return ClientResponseDto.builder()
                .id(savedClient.getId())
                .firstName(savedClient.getFirstName())
                .lastName(savedClient.getLastName())
                .email(savedClient.getEmail())
                .phoneNumber(savedClient.getPhoneNumber())
                .build();
    }
}
