package ru.otus.spring.booklibrary.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.dto.GenreDto;
import ru.otus.spring.booklibrary.service.LibraryService;

import java.util.List;
import java.util.Optional;

import static ru.otus.spring.booklibrary.service.DtoConverter.convertListToMap;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final LibraryService libraryService;
    private final ConsoleProcessor consoleProcessor;
    private final AuthorController authorController;

    public void addBookToLibrary() {
        BookDto.BookDtoBuilder bookDtoBuilder = BookDto.builder();
        bookDtoBuilder.bookTitle(consoleProcessor.waitAnswer("Enter book's name please"));
        bookDtoBuilder.genre(selectGenre().getGenreName());
        bookDtoBuilder.authors(authorController.inputAuthors());

        BookDto book = libraryService.addBookToLibrary(bookDtoBuilder.build());
        System.out.println("Congratulate, your book was added to database");
    }

    public void deleteBookFromLibrary() {
        List<BookDto> foundBooks = findBookByNameInDatabase();
        if (checkIsNotEmpty(foundBooks)) {
            BookDto bookForDelete = foundBooks.size() == 1 ? foundBooks.get(0) : selectBook(foundBooks);
            libraryService.deleteBookFromLibrary(bookForDelete);
            System.out.println("Congratulate, your books was deleted from database");
        }
    }

    private BookDto selectBook(List<BookDto> foundBooks) {
        return consoleProcessor.waitAndCheckAnswer(convertListToMap(foundBooks),
                "Please, choose book number for deleting");
    }

    public void findBookByName() {
        List<BookDto> foundBooks = findBookByNameInDatabase();
        if (checkIsNotEmpty(foundBooks)) {
            foundBooks.forEach(System.out::println);
        }
    }

    public Optional<BookDto> findOneBook() {
        List<BookDto> dtoList = findBookByNameInDatabase();

        if (dtoList.size() == 1) {
            return Optional.ofNullable(dtoList.get(0));
        }

        if (checkIsNotEmpty(dtoList)) {
            return Optional.ofNullable(selectBook(dtoList));
        }

        return Optional.empty();
    }

    private List<BookDto> findBookByNameInDatabase() {
        String bookName =consoleProcessor.waitAnswer("Please, enter book's name");
        return libraryService.findBookByName(bookName);
    }

    private boolean checkIsNotEmpty(List<BookDto> bookList) {
        if (bookList.isEmpty()) {
            System.out.println("Sorry, that book wasn't found in database");
            return false;
        }

        return true;
    }

    private GenreDto selectGenre() {
        List<GenreDto> genres = libraryService.getAllGenres();
        return consoleProcessor.waitAndCheckAnswer(convertListToMap(genres), "Please, choose genre number");
    }
}
