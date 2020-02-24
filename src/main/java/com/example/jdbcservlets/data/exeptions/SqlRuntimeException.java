package com.example.jdbcservlets.data.exeptions;

public class SqlRuntimeException extends RuntimeException {
    public SqlRuntimeException(Throwable cause) {
        super(cause);
    }
}
