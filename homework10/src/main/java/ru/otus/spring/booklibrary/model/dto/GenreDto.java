package ru.otus.spring.booklibrary.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GenreDto {

  private final String genreName;

  @Override
  public String toString() {
    return genreName;
  }
}
