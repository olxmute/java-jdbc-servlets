package com.jdbcservlets.dto;

import lombok.Data;

@Data
public class ClientCreateDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
