package ru.otus.spring.booklibrary.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.booklibrary.model.entity.Author;

import java.util.Optional;

public interface AuthorDao extends CrudRepository<Author, Long> {
    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
}
