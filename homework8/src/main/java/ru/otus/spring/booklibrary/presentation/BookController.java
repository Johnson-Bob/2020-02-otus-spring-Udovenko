package ru.otus.spring.booklibrary.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.dto.GenreDto;
import ru.otus.spring.booklibrary.service.LibraryService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final LibraryService libraryService;
    private final ConsoleProcessor consoleProcessor;
    private final AuthorController authorController;

    public void addBookToLibrary() {
        BookDto.BookDtoBuilder bookDtoBuilder = BookDto.builder();
        bookDtoBuilder.bookName(consoleProcessor.waitAnswer("Enter book's name please"));
        bookDtoBuilder.genreDto(selectGenre());
        bookDtoBuilder.authors(authorController.inputAuthors());

        BookDto book = libraryService.addBookToLibrary(bookDtoBuilder.build());
        System.out.println(String.format("Congratulate, your book was added to database with id = %d", book.getId()));
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
        Map<Long, BookDto> booksMap = foundBooks.stream().collect(toMap(BookDto::getId, identity()));
        return consoleProcessor.waitAndCheckAnswer(booksMap, "Please, choose book number for deleting");
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
        Map<Long, GenreDto> genreMap = libraryService.getAllGenres().stream().collect(toMap(GenreDto::getId, identity()));
        return consoleProcessor.waitAndCheckAnswer(genreMap, "Please, choose genre number");
    }
}
