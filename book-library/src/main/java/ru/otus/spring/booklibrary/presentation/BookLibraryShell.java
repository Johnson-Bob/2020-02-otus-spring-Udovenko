package ru.otus.spring.booklibrary.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class BookLibraryShell {
    private final ConsoleController consoleController;

    @ShellMethod(value = "Create book and add to database", key = {"create", "c"})
    public void createBook() {
        consoleController.addBookToLibrary();
    }

    @ShellMethod(value = "Delete book from database", key = {"delete", "d"})
    public void deleteBook() {
        consoleController.deleteBookFromLibrary();
    }

    @ShellMethod(value = "Find book by name", key = {"find", "f"})
    public void findBook() {
        consoleController.findBookByName();
    }
}
