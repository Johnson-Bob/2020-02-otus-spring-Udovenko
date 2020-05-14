package ru.otus.spring.booklibrary.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.booklibrary.service.LibraryService;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final LibraryService libraryService;
    private final AuthorController authorController;

    public void addBookToLibrary() {

    }

    public void deleteBookFromLibrary() {

    }

    public void findBookByName() {

    }
}
