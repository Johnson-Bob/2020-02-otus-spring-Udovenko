package ru.otus.spring.booklibrary.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.booklibrary.model.entity.Comment;

public interface CommentDao extends CrudRepository<Comment, Long> {

}
