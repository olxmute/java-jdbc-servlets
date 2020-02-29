package com.jdbcservlets.dto;

import lombok.Data;

@Data
public class ClientUpdateDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
