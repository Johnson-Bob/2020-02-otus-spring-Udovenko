package ru.otus.spring.booklibrary.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.booklibrary.model.entity.Book;

import java.util.List;

public interface BookDao extends MongoRepository<Book, String> {

    List<Book> findByTitle(String title);
}
