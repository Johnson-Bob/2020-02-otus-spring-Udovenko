package ru.otus.spring.booklibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.booklibrary.dao.BookDao;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.dto.CommentDto;
import ru.otus.spring.booklibrary.model.entity.Book;
import ru.otus.spring.booklibrary.model.entity.Comment;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private BookDao bookDao;

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getAllBookComments(BookDto bookDto) {
        return bookDao.findById(bookDto.getId())
                .map(DtoConverter::convertToListCommentDto)
                .orElse(Collections.emptyList());
    }

    @Override
    @Transactional
    public void saveComment(CommentDto dto) {
        Comment comment = new Comment(dto.getText(), LocalDateTime.now());
        Book book = bookDao.findById(dto.getBookDto().getId()).orElseThrow();
        book.addComment(comment);
        bookDao.save(book);
    }

    @Override
    @Transactional
    public void deleteComment(CommentDto dto) {
        Comment comment = new Comment(dto.getText(), dto.getCreate());
        Book book = bookDao.findById(dto.getBookDto().getId()).orElseThrow();
        book.deleteComment(comment);
        bookDao.save(book);
    }
}
