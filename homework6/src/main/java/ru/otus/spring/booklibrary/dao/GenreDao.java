package ru.otus.spring.booklibrary.dao;

import ru.otus.spring.booklibrary.model.entity.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getAllGenres();
}
