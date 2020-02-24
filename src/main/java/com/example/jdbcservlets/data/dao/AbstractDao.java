package com.example.jdbcservlets.data.dao;

import com.example.jdbcservlets.data.exeptions.SqlRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Slf4j
public abstract class AbstractDao<T, ID> implements GenericDao<T, ID> {

    private Connection connection;

    public abstract String getSelectQuery();

    protected abstract List<T> parseResultSet(ResultSet resultSet);

    @Override
    public Optional<T> findById(ID id) {
        String query = getSelectQuery();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> res = Optional.ofNullable(parseResultSet(resultSet)).orElse(emptyList());
            if (res.size() > 1) {
                throw new SQLException("Received more than one record.");
            }
            return res.stream().findFirst();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new SqlRuntimeException(e);
        }
    }

    @Override
    public List<T> findAll() {
        String query = getSelectQuery();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return Optional.ofNullable(parseResultSet(resultSet)).orElse(emptyList());
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new SqlRuntimeException(e);
        }
    }

}
