package com.jdbcservlets.data.queries;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ClientsSqlQueries {

    public final String SELECT_ALL_QUERY = "SELECT * FROM clients";

    public final String SELECT_BY_ID_QUERY = "SELECT * FROM clients WHERE id = ?";

    public final String INSERT_QUERY = """
            INSERT INTO clients(first_name, last_name, email, password, phone_number)
               VALUES (?, ?, ?, ?, ?)
            """;

    public final String UPDATE_QUERY = """
            UPDATE clients SET
                first_name = ?,
                last_name = ?,
                email = ?,
                phone_number = ?
            WHERE (id = ?)
            """;

    public final String DELETE_BY_ID = "DELETE FROM clients WHERE id = ?";

}
