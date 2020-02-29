package com.jdbcservlets.exeptions;

public class PersistenceException extends RuntimeException {
    public PersistenceException(Throwable cause) {
        super(cause);
    }
}
