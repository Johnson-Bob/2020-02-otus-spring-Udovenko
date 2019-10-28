package ru.otus.spring.booklibrary.dao;

import ru.otus.spring.booklibrary.model.entity.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> getAllUsed();
    List<Author> getByBookId(long id);
    List<Author> getAllAuthors();
}
