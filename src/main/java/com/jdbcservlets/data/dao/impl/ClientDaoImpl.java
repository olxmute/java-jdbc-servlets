package com.jdbcservlets.data.dao.impl;

import com.jdbcservlets.data.dao.AbstractDao;
import com.jdbcservlets.data.dao.ClientDao;
import com.jdbcservlets.data.domain.Client;
import com.jdbcservlets.data.exeptions.PersistenceException;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.jdbcservlets.data.queries.ClientsSqlQueries.DELETE_BY_ID;
import static com.jdbcservlets.data.queries.ClientsSqlQueries.INSERT_QUERY;
import static com.jdbcservlets.data.queries.ClientsSqlQueries.SELECT_ALL_QUERY;
import static com.jdbcservlets.data.queries.ClientsSqlQueries.SELECT_BY_ID_QUERY;

@Slf4j
public class ClientDaoImpl extends AbstractDao<Client, Long> implements ClientDao {

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
    protected String getDeleteQuery() {
        return DELETE_BY_ID;
    }

    @Override
    protected List<Client> parseResultSet(ResultSet resultSet) {
        return null;
    }

    @Override
    protected void prepareInsertStatement(PreparedStatement statement, Client client) {
        try {
            statement.setString(1, client.getId().toString());
            statement.setString(2, client.getFirstName());
            statement.setString(3, client.getLastName());
            statement.setString(4, client.getEmail());
            statement.setString(5, client.getPassword());
            statement.setString(6, client.getPhoneNumber());
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }
}
