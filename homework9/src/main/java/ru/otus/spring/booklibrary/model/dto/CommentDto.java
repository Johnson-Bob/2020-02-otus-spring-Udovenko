package ru.otus.spring.booklibrary.model.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDto {
  private final String text;
  private final BookDto bookDto;
  private final LocalDateTime create;

  @Override
  public String toString() {
    return text;
  }
}
