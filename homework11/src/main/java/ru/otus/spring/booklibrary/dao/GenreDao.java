package ru.otus.spring.booklibrary.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.booklibrary.model.entity.Genre;

public interface GenreDao extends MongoRepository<Genre, String> {

}
