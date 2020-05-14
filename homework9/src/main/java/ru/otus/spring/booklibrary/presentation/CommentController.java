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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/books/comments/{bookId}")
    public List<CommentModel> outputAllBookComments(@PathVariable String bookId) {
        BookDto bookDto = BookDto.builder().id(bookId).build();
        List<CommentDto> comments = commentService.getAllBookComments(bookDto);
        return comments.stream()
                .map(this::toCommentModel)
                .collect(Collectors.toList());
    }

    @PostMapping("/books/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCommentToBook(@RequestBody CommentModel model) {
        CommentDto commentDto = toCommentDto(model);
        commentService.saveComment(commentDto);
    }

    @DeleteMapping("/books/comments")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBookComment(@RequestBody CommentModel model) {
        CommentDto commentDto = toCommentDto(model);
        commentService.deleteComment(commentDto);
    }

    private CommentModel toCommentModel(CommentDto dto) {
        if (dto == null) {
            return null;
        }

        CommentModel commentModel = new CommentModel();
        commentModel.setText(dto.getText());
        commentModel.setCreate(dto.getCreate());
        commentModel.setBookId(dto.getBookDto().getId());

        return commentModel;
    }

    private CommentDto toCommentDto(CommentModel model) {
        if (model == null) {
            return null;
        }

        return CommentDto.builder()
                .text(model.getText())
                .bookDto(BookDto.builder().id(model.getBookId()).build())
                .create(model.getCreate())
                .build();
    }
}
