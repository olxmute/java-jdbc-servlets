package com.jdbcservlets.converter;

import com.jdbcservlets.data.domain.Client;
import com.jdbcservlets.dto.ClientResponseDto;

public class ClientToResponseDtoConverter implements Converter<Client, ClientResponseDto> {
    @Override
    public ClientResponseDto convert(Client client) {
        return ClientResponseDto.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }
}
