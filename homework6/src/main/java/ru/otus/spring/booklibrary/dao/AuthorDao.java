package ru.otus.spring.booklibrary.dao;

import ru.otus.spring.booklibrary.model.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    List<Author> getAllAuthors();
    Optional<Author> findByFirstAndLastNames (String firstName, String lastName);
    Author save(Author author);
}
