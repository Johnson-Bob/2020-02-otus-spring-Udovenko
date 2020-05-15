package ru.otus.spring.booklibrary.service;

import ru.otus.spring.booklibrary.model.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> getAllGenres();
}
