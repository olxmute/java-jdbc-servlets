package com.jdbcservlets.data.dao.impl;

import com.jdbcservlets.data.config.ConnectionFactory;
import com.jdbcservlets.data.dao.AbstractDao;
import com.jdbcservlets.data.dao.AccountDao;
import com.jdbcservlets.data.domain.Account;
import com.jdbcservlets.data.mapper.AccountMapper;
import com.jdbcservlets.exceptions.PersistenceException;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.jdbcservlets.data.queries.AccountSqlQueries.DELETE_BY_ID_QUERY;
import static com.jdbcservlets.data.queries.AccountSqlQueries.INSERT_QUERY;
import static com.jdbcservlets.data.queries.AccountSqlQueries.SELECT_ALL_QUERY;
import static com.jdbcservlets.data.queries.AccountSqlQueries.SELECT_BY_ID_QUERY;
import static com.jdbcservlets.data.queries.AccountSqlQueries.UPDATE_QUERY;


@Slf4j
public class AccountDaoImpl extends AbstractDao<Account, Long> implements AccountDao {

    public AccountDaoImpl() {
        super(ConnectionFactory.getConnection(), new AccountMapper());
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
        return DELETE_BY_ID_QUERY;
    }

    @Override
    protected void prepareInsertStatement(PreparedStatement statement, Account account) {
        try {
            statement.setString(1, account.getAccountNumber());
            statement.setString(2, account.getCurrency().name());
            statement.setBigDecimal(3, account.getBalance());
            statement.setString(4, account.getClientId().toString());
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement statement, Account account) {
        try {
            statement.setString(1, account.getAccountNumber());
            statement.setString(2, account.getCurrency().name());
            statement.setBigDecimal(3, account.getBalance());
            statement.setString(4, account.getId().toString());
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }
}
