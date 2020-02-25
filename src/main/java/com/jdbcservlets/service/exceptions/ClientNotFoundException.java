package com.jdbcservlets.service.exceptions;

public class ClientNotFoundException extends EntryNotFountException {
    public ClientNotFoundException(String message) {
        super(message);
    }
}
