package com.jdbcservlets.exceptions;

public class EntryNotFountException extends RuntimeException {
    public EntryNotFountException(String message) {
        super(message);
    }
}
