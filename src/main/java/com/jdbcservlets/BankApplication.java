package com.jdbcservlets;

import com.jdbcservlets.server.EmbeddedServer;

public class BankApplication {
    public static void main(String[] args) {
        EmbeddedServer.start(BankApplication.class);
    }
}
