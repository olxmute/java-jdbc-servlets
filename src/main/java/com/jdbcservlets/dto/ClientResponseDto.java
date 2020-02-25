package com.jdbcservlets.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClientResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
