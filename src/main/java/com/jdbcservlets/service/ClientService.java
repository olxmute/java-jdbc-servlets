package com.jdbcservlets.service;

import com.jdbcservlets.dto.ClientCreateDto;
import com.jdbcservlets.dto.ClientResponseDto;

import java.util.List;

public interface ClientService {

    ClientResponseDto create(ClientCreateDto createDto);

    ClientResponseDto findById(Long id);

    List<ClientResponseDto> findAll();

    void deleteById(Long id);
}
