package ru.otus.spring.booklibrary.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.dto.CommentDto;
import ru.otus.spring.booklibrary.model.web.CommentModel;
import ru.otus.spring.booklibrary.service.CommentService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.spring.booklibrary.presentation.ModelDtoConverter.toCommentDto;
import static ru.otus.spring.booklibrary.presentation.ModelDtoConverter.toCommentModelList;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/books/comments/{bookId}")
    public List<CommentModel> getAllBookComments(@PathVariable String bookId) {
        BookDto bookDto = BookDto.builder().id(bookId).build();
        List<CommentDto> comments = commentService.getAllBookComments(bookDto);
        return toCommentModelList(comments);
    }

    @PostMapping("/books/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCommentToBook(@Valid @RequestBody CommentModel model) {
        CommentDto commentDto = toCommentDto(model);
        commentService.saveComment(commentDto);
    }

    @DeleteMapping("/books/comments")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBookComment(@Valid @RequestBody CommentModel model) {
        CommentDto commentDto = toCommentDto(model);
        commentService.deleteComment(commentDto);
    }
}
