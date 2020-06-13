package ru.otus.spring.booklibrary.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.spring.booklibrary.model.entity.Author;

public interface AuthorDao extends MongoRepository<Author, String> {
  Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
}
