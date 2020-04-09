package ru.otus.spring.booklibrary.dao;

import ru.otus.spring.booklibrary.model.entity.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAll();
    Book getById(long id);
    List<Book> getByName(String name);
    long create(Book book);
    int delete(long id);
}
