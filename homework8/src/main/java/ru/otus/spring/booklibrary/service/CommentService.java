package ru.otus.spring.booklibrary.service;

import ru.otus.spring.booklibrary.model.dto.CommentDto;

public interface CommentService {
    void saveComment(CommentDto dto);
    void deleteComment(CommentDto dto);
}
