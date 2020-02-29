package com.jdbcservlets.data.mapper;

import com.jdbcservlets.data.domain.Client;
import com.jdbcservlets.exceptions.PersistenceException;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ClientMapper implements ResultSetMapper<Client> {
    @Override
    public List<Client> map(ResultSet resultSet) {

        List<Client> clients = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Client client = Client.builder()
                        .id(resultSet.getLong("id"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .phoneNumber(resultSet.getString("phone_number"))
                        .build();

                clients.add(client);
            }
        } catch (SQLException e) {
            log.error("Cannot parse ResultSet to Client", e);
            throw new PersistenceException(e);
        }

        return clients;
    }
}
