package ru.otus.spring.booklibrary.service;

import java.util.List;

import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.dto.CommentDto;

public interface CommentService {
  List<CommentDto> getAllBookComments(BookDto bookDto);

  void saveComment(CommentDto dto);

  void deleteComment(CommentDto dto);
}
