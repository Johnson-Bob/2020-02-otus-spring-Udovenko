package ru.otus.spring.booklibrary.service;

import java.util.List;

import ru.otus.spring.booklibrary.model.dto.GenreDto;

public interface GenreService {
  List<GenreDto> getAllGenres();
}
