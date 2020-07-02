package com.jdbcservlets.data.mapper;

import com.jdbcservlets.data.domain.Account;
import com.jdbcservlets.data.domain.Currency;
import com.jdbcservlets.exceptions.PersistenceException;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AccountMapper implements ResultSetMapper<Account> {
    @Override
    public List<Account> map(ResultSet resultSet) {

        List<Account> accounts = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Account account = Account.builder()
                        .id(resultSet.getLong("id"))
                        .accountNumber(resultSet.getString("account_number"))
                        .currency(Currency.valueOf(resultSet.getString("currency")))
                        .balance(resultSet.getBigDecimal("balance"))
                        .clientId(resultSet.getLong("client_id"))
                        .build();

                accounts.add(account);
            }
        } catch (SQLException e) {
            log.error("Cannot parse ResultSet to Account", e);
            throw new PersistenceException(e);
        }

        return accounts;
    }
}
