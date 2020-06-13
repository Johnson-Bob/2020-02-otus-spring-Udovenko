package ru.otus.spring.booklibrary.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.spring.booklibrary.model.entity.Book;

public interface BookDao extends MongoRepository<Book, String> {

  List<Book> findByTitle(String title);
}
