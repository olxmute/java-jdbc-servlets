package com.jdbcservlets.service.impl;

import com.jdbcservlets.converter.ClientToResponseDtoConverter;
import com.jdbcservlets.data.dao.ClientDao;
import com.jdbcservlets.data.dao.impl.ClientDaoImpl;
import com.jdbcservlets.data.domain.Client;
import com.jdbcservlets.dto.ClientCreateDto;
import com.jdbcservlets.dto.ClientResponseDto;
import com.jdbcservlets.service.ClientService;
import com.jdbcservlets.service.exceptions.ClientNotFoundException;
import com.jdbcservlets.utils.GenerationUtils;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ClientServiceImpl implements ClientService {

    private static final String COMPANY_NAME = "google";

    private final ClientDao clientDao = new ClientDaoImpl();

    private final ClientToResponseDtoConverter converter = new ClientToResponseDtoConverter();

    @Override
    public ClientResponseDto create(ClientCreateDto createDto) {
        String password = GenerationUtils.generatePassword();
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

        return converter.convert(savedClient);
    }

    @Override
    public ClientResponseDto findById(Long id) {
        return converter.convert(this.getClientById(id));
    }

    @Override
    public List<ClientResponseDto> findAll() {
        return clientDao.findAll()
                .stream()
                .map(converter::convert)
                .collect(toList());
    }

    @Override
    public void deleteById(Long id) {
        clientDao.delete(this.getClientById(id));
    }

    private Client getClientById(Long id) {
        return clientDao.findById(id).orElseThrow(() -> new ClientNotFoundException("Client not found!"));
    }

}
