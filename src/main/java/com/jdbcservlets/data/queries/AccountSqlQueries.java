package com.jdbcservlets.data.queries;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountSqlQueries {

    public final String SELECT_ALL_QUERY = "SELECT * FROM accounts";

    public final String SELECT_BY_ID_QUERY = "SELECT * FROM accounts WHERE id = ?";

    public final String SELECT_BY_ACCOUNT_NUMBER_QUERY = "SELECT * FROM accounts WHERE account_number = ?";

    public final String SELECT_ALL_BY_CLIENT_ID_QUERY = "SELECT * FROM accounts WHERE id = ?";

    public final String INSERT_QUERY = """
            INSERT INTO accounts(account_number, currency, balance, client_id)
               VALUES (?, ?, ?, ?)
            """;

    public final String UPDATE_QUERY = """
            UPDATE accounts SET
                account_number = ?,
                currency = ?,
                balance = ?
            WHERE (id = ?)
            """;

    public final String UPDATE_BALANCE_QUERY = "UPDATE accounts SET balance = ? WHERE (id = ?)";

    public final String DELETE_BY_ID_QUERY = "DELETE FROM accounts WHERE id = ?";

    public final String DELETE_ALL_BY_CLIENT_ID_QUERY = "DELETE FROM accounts WHERE client_id = ?";

}
