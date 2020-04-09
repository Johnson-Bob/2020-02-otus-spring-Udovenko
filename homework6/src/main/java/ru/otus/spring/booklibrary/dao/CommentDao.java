package ru.otus.spring.booklibrary.dao;

import ru.otus.spring.booklibrary.model.entity.Comment;

import java.util.List;

public interface CommentDao {
    List<Comment> findByBook_Id(Long bookId);
    Comment createComment(Comment comment);
    void deleteCommentById(Long id);
}
