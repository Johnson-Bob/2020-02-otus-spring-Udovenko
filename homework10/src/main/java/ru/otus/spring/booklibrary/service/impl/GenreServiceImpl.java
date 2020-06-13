package ru.otus.spring.booklibrary.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.booklibrary.dao.GenreDao;
import ru.otus.spring.booklibrary.model.dto.GenreDto;
import ru.otus.spring.booklibrary.service.GenreService;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
  private final GenreDao genreDao;

  @Override
  public List<GenreDto> getAllGenres() {
    return genreDao.findAll().stream().map(g -> new GenreDto(g.getGenreName()))
      .collect(Collectors.toList());
  }
}
