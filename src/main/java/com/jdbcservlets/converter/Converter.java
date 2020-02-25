package com.jdbcservlets.converter;

public interface Converter<E, T> {
    T convert(E object);
}
