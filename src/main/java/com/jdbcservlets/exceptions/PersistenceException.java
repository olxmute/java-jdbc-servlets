package com.jdbcservlets.exceptions;

public class PersistenceException extends RuntimeException {
    public PersistenceException(Throwable cause) {
        super(cause);
    }
}
