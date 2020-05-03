package ru.otus.spring.booklibrary.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.booklibrary.model.entity.Book;

import java.util.List;

public interface BookDao extends CrudRepository<Book, Long> {

    @EntityGraph(value = "bookGenre")
    List<Book> findByName(String name);
}
