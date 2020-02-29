package com.jdbcservlets.data.dao.impl;

import com.jdbcservlets.data.config.ConnectionFactory;
import com.jdbcservlets.data.dao.AbstractDao;
import com.jdbcservlets.data.dao.ClientDao;
import com.jdbcservlets.data.domain.Client;
import com.jdbcservlets.data.mapper.ClientMapper;
import com.jdbcservlets.exceptions.PersistenceException;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.jdbcservlets.data.queries.ClientsSqlQueries.DELETE_BY_ID;
import static com.jdbcservlets.data.queries.ClientsSqlQueries.INSERT_QUERY;
import static com.jdbcservlets.data.queries.ClientsSqlQueries.SELECT_ALL_QUERY;
import static com.jdbcservlets.data.queries.ClientsSqlQueries.SELECT_BY_ID_QUERY;
import static com.jdbcservlets.data.queries.ClientsSqlQueries.UPDATE_QUERY;

@Slf4j
public class ClientDaoImpl extends AbstractDao<Client, Long> implements ClientDao {

    public ClientDaoImpl() {
        super(ConnectionFactory.getConnection(), new ClientMapper());
    }

    @Override
    protected String getSelectQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    protected String getSelectByIdQuery() {
        return SELECT_BY_ID_QUERY;
    }

    @Override
    protected String getInsertQuery() {
        return INSERT_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE_BY_ID;
    }

    @Override
    protected void prepareInsertStatement(PreparedStatement statement, Client client) {
        try {
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getPassword());
            statement.setString(5, client.getPhoneNumber());
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement statement, Client client) {
        try {
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getPhoneNumber());
            statement.setString(5, client.getId().toString());
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }
}
