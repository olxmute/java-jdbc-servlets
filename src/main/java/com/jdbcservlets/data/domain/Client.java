package com.jdbcservlets.data.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Client implements Persistable<Long> {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;

}
