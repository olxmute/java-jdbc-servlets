package com.jdbcservlets.exceptions;

public class ClientNotFoundException extends EntryNotFountException {
    public ClientNotFoundException(String message) {
        super(message);
    }
}
