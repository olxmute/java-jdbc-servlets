package com.jdbcservlets.data.config;

import com.jdbcservlets.exceptions.ConnectionException;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class ConnectionFactory {

    private static final String CONNECTION_FAILURE_MSG = "Connection to DB failed";

    private static final String URL = "jdbc:mysql://localhost:3306/bankapplication?user=root&password=1234";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            log.error(CONNECTION_FAILURE_MSG, e);
            throw new ConnectionException(CONNECTION_FAILURE_MSG, e);
        }
    }

}
