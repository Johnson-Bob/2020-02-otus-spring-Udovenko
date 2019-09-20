package ru.otus.spring.studenttestingsboot.dao;

import java.util.Collections;
import java.util.List;

public interface Dao<T> {
    List<T> getAll();
    T save(T entity);
    T getById(long id);
    default List<T> updateCache() {return Collections.emptyList();}
}
