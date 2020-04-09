package ru.otus.spring.booklibrary.dao;

import ru.otus.spring.booklibrary.model.entity.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAll();
    Book getById(Long id);
    List<Book> getByName(String name);
    Book save(Book book);
    void deleteById(Long id);
}
