package com.example.jdbcservlets.data.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, ID> {

    Optional<T> findById(ID id);

    List<T> findAll();

    T save(T entity);

    void deleteById(ID id);

    void delete(T object);
}
