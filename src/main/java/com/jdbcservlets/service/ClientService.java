package com.jdbcservlets.service;

import com.jdbcservlets.dto.ClientCreateDto;
import com.jdbcservlets.dto.ClientResponseDto;
import com.jdbcservlets.dto.ClientUpdateDto;

import java.util.List;

public interface ClientService {

    ClientResponseDto create(ClientCreateDto createDto);

    ClientResponseDto update(ClientUpdateDto updateDto, Long id);

    ClientResponseDto findById(Long id);

    List<ClientResponseDto> findAll();

    void deleteById(Long id);
}
