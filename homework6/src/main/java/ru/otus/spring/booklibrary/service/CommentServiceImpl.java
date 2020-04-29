package ru.otus.spring.booklibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.booklibrary.dao.CommentDao;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.dto.CommentDto;
import ru.otus.spring.booklibrary.model.entity.Book;
import ru.otus.spring.booklibrary.model.entity.Comment;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Override
    @Transactional(readOnly = true)
    public Map<Long, CommentDto> getAllBookComments(BookDto bookDto) {
        return commentDao.findByBookId(bookDto.getId()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toMap(CommentDto::getId, Function.identity()));
    }

    @Override
    @Transactional
    public void saveComment(CommentDto dto) {
        commentDao.createComment(convertToComment(dto));
    }

    @Override
    @Transactional
    public void deleteComment(CommentDto dto) {
        commentDao.deleteCommentById(dto.getId());
    }

    private CommentDto convertToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .bookDto(BookDto.builder().id(comment.getBook().getId()).bookName(comment.getBook().getName()).build())
                .text(comment.getText())
                .build();
    }

    private Comment convertToComment(CommentDto commentDto) {
        BookDto bookDto = commentDto.getBookDto();
        Book book = new Book(bookDto.getId(), bookDto.getBookName(), bookDto.getGenre(), bookDto.getAuthors());
        return new Comment(commentDto.getId(), book, commentDto.getText());
    }
}
