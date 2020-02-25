package com.jdbcservlets.dto;

import lombok.Value;

@Value
public class ClientCreateDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
