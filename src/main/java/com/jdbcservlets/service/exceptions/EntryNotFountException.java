package com.jdbcservlets.service.exceptions;

public class EntryNotFountException extends RuntimeException {
    public EntryNotFountException(String message) {
        super(message);
    }
}
