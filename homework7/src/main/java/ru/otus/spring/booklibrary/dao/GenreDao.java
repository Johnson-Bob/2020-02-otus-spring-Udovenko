package ru.otus.spring.booklibrary.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.booklibrary.model.entity.Genre;

public interface GenreDao extends CrudRepository<Genre, Long> {

}
