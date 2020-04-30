package ru.otus.spring.booklibrary.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class BookLibraryShell {
    private final BookController bookController;
    private final AuthorController authorController;
    private final CommentController commentController;

    @ShellMethod(value = "Create book and add to database", key = {"create", "c"})
    public void createBook() {
        bookController.addBookToLibrary();
    }

    @ShellMethod(value = "Delete book from database", key = {"delete", "d"})
    public void deleteBook() {
        bookController.deleteBookFromLibrary();
    }

    @ShellMethod(value = "Find book by name", key = {"find", "f"})
    public void findBook() {
        bookController.findBookByName();
    }

    @ShellMethod(value = "Get all authors", key = "get-all-authors")
    public void getAllAuthors() {
        authorController.outputAllAuthors();
    }

    @ShellMethod(value = "Get all book comments ", key = "book-comments")
    public void getAllBookComments() {
        commentController.outputAllBookComments();
    }

    @ShellMethod(value = "Add a comment to the book", key = "add-comment")
    public void addBokComment() {
        commentController.addCommentToBook();
    }

    @ShellMethod(value = "Delete book comment", key = "delete-comment")
    public void deleteBookComment() {
        commentController.deleteBookComment();
    }
}
