package ru.otus.spring.booklibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.booklibrary.dao.CommentDao;
import ru.otus.spring.booklibrary.model.dto.CommentDto;

import static ru.otus.spring.booklibrary.service.DtoConverter.convertToComment;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Override
    @Transactional
    public void saveComment(CommentDto dto) {
        commentDao.save(convertToComment(dto));
    }

    @Override
    @Transactional
    public void deleteComment(CommentDto dto) {
        commentDao.deleteById(dto.getId());
    }
}
