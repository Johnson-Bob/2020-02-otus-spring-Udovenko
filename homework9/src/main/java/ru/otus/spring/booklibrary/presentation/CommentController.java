package ru.otus.spring.booklibrary.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.booklibrary.service.CommentService;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final BookController bookController;
    private final CommentService commentService;

    public void outputAllBookComments() {

    }

    public void addCommentToBook() {

    }

    public void deleteBookComment() {

    }
}
