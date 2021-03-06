package ru.otus.spring.booklibrary.service;

import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAllBookComments(BookDto bookDto);
    void saveComment(CommentDto dto);
    void deleteComment(CommentDto dto);
}
