package ru.otus.spring.dao;

import java.util.List;

public interface Dao<T> {
    List<T> getAll();
    T save(T entity);
    T getById(long id);
}
