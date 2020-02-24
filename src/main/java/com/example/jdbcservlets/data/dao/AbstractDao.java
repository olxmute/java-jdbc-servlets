package com.example.jdbcservlets.data.dao;

import com.example.jdbcservlets.data.domain.Persistable;
import com.example.jdbcservlets.data.exeptions.PersistenceException;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Slf4j
public abstract class AbstractDao<T extends Persistable<ID>, ID> implements GenericDao<T, ID> {

    private Connection connection;

    protected abstract String getSelectQuery();

    protected abstract String getSelectByIdQuery();

    protected abstract String getCreateQuery();

    protected abstract String getDeleteQuery();

    protected abstract List<T> parseResultSet(ResultSet resultSet);

    protected abstract void prepareInsertStatement(PreparedStatement statement, T object);

    @Override
    public Optional<T> findById(ID id) {
        String query = getSelectByIdQuery();
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
            throw new PersistenceException(e);
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
            throw new PersistenceException(e);
        }
    }

    @Override
    public T save(T object) {
        String createQuery = getCreateQuery();
        try (PreparedStatement statement = connection.prepareStatement(createQuery)) {
            prepareInsertStatement(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new SQLException("On persist modified more then 1 record: " + count);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
        String selectQuery = getSelectQuery() + " WHERE id = last_insert_id()";
        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            ResultSet rs = statement.executeQuery();
            List<T> list = parseResultSet(rs);
            if ((list == null) || (list.size() != 1)) {
                throw new SQLException("Exception on findByPK new persist data.");
            }
            return list.get(0);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteById(ID id) {
        String sql = getDeleteQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new SQLException("On delete modified more then 1 record: " + count);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }

    @Override
    public void delete(T object) {
        this.deleteById(object.getId());
    }

}
