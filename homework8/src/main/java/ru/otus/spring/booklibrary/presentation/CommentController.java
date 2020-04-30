package ru.otus.spring.booklibrary.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.dto.CommentDto;
import ru.otus.spring.booklibrary.service.CommentService;
import ru.otus.spring.booklibrary.service.LibraryService;

import java.util.Map;
import java.util.function.Consumer;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final BookController bookController;
    private final ConsoleProcessor consoleProcessor;
    private final CommentService commentService;
    private final LibraryService libraryService;

    public void outputAllBookComments() {
        handleCommentsByBook(bookDto -> {
            Map<Long, CommentDto> comments = libraryService.getAllBookComments(bookDto);
            String noComments = "This book don't have any comments yet";
            if (!comments.isEmpty()) {
                consoleProcessor.displayMapOnScreen(comments);
            } else {
                consoleProcessor.outputString(noComments);
            }
        });
    }

    public void addCommentToBook() {
        handleCommentsByBook(bookDto -> {
            String comment = consoleProcessor.waitAnswer("Write your comment");
            CommentDto comDto = CommentDto.builder().bookDto(bookDto).text(comment).build();
            commentService.saveComment(comDto);
            consoleProcessor.outputString("Congratulate, your comment has been saved");
        });
    }

    public void deleteBookComment() {
        handleCommentsByBook(bookDto -> {
            Map<Long, CommentDto> comments = libraryService.getAllBookComments(bookDto);
            CommentDto selectedComment = consoleProcessor.waitAndCheckAnswer(comments, "Please, select comment for deleting");
            commentService.deleteComment(selectedComment);
            consoleProcessor.outputString("Congratulate, your comment has been deleted");
        });
    }

    private void handleCommentsByBook(Consumer<BookDto> consumer) {
        bookController.findOneBook().ifPresent(consumer);
    }
}
