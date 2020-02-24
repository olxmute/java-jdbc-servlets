package com.jdbcservlets.data.mapper;

import com.jdbcservlets.data.domain.Persistable;

import java.sql.ResultSet;
import java.util.List;

public interface ResultSetMapper<T extends Persistable<?>> {
    List<T> map(ResultSet resultSet);
}
