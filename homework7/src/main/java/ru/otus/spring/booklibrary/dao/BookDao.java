package ru.otus.spring.booklibrary.dao;

import ru.otus.spring.booklibrary.model.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    List<Book> getAll();
    Optional<Book> getById(Long id);
    List<Book> getByName(String name);
    Book save(Book book);
    void deleteById(Long id);
}
