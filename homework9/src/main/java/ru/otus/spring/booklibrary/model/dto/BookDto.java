package ru.otus.spring.booklibrary.model.dto;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookDto {
  private final String id;
  private final String bookTitle;
  private final String genre;
  private final Set<AuthorDto> authors;

  @Override
  public String toString() {
    return "\"" + bookTitle + "\" " + genre + " "
      + authors.stream().map(AuthorDto::shortString).collect(Collectors.joining(", "));
  }
}
