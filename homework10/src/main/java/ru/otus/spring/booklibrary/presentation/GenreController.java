package ru.otus.spring.booklibrary.presentation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.booklibrary.model.dto.GenreDto;
import ru.otus.spring.booklibrary.service.GenreService;

@RestController
@RequiredArgsConstructor
public class GenreController {

  @Autowired
  private final GenreService service;

  @GetMapping("/genres")
  public List<String> getAllGenres() {
    final List<GenreDto> allGenres = service.getAllGenres();
    return allGenres.stream().map(GenreDto::getGenreName).collect(Collectors.toList());
  }
}
