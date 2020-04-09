package ru.otus.spring.booklibrary.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.entity.Author;
import ru.otus.spring.booklibrary.model.entity.Book;
import ru.otus.spring.booklibrary.model.entity.Genre;
import ru.otus.spring.booklibrary.service.LibraryService;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ConsoleController {
    private final LibraryService libraryService;
    private Scanner scanner = new Scanner(System.in);

    public void addBookToLibrary() {
        BookDto.BookDtoBuilder bookDtoBuilder = BookDto.builder();
        bookDtoBuilder.bookName(waitAnswer("Enter book's name please"));
        bookDtoBuilder.genre(chooseGenre());
        bookDtoBuilder.authors(chooseAuthors());

        Book book = libraryService.addBookToLibrary(bookDtoBuilder.build());
        System.out.println(String.format("Congratulate, your book was added to database with id = %d", book.getId()));
    }

    public void deleteBookFromLibrary() {
        List<Book> bookForDeleting = chooseBooks();
        if (chooseBooks().isEmpty()) {
            System.out.println("Sorry, database don't have any book");
        } else {
            bookForDeleting.forEach(libraryService::deleteBookFromLibrary);
            System.out.println("Congratulate, your books was deleted from database");
        }
    }

    public void findBookByName() {
        String bookName = waitAnswer("Please, enter book's name");
        List<Book> foundBooks = libraryService.findBookByName(bookName);
        if (foundBooks.isEmpty()) {
            System.out.println("Sorry, that book wasn't found in database");
        } else {
            foundBooks.forEach(System.out::println);
        }
    }

    private List<Book> chooseBooks() {
        Map<Long, Book> bookMap = libraryService.getAllBooksFromLibrary().stream().collect(Collectors.toMap(Book::getId, Function.identity()));
        if (bookMap.isEmpty()) return Collections.EMPTY_LIST;

        displayMapOnScreen(bookMap);
        List<Book> bookList = null;
        do {
            String answer = waitAnswer("Please, choose books, separate their numbers by comma", Pattern.compile("^\\d+(,?\\s*\\d*)*"));
            bookList = checkAnswersInMap(answer, bookMap);
        } while (bookList == null || bookList.isEmpty());

        return bookList;
    }

    private Genre chooseGenre() {
        Map<Long, Genre> genreMap = libraryService.getAllGenres().stream().collect(Collectors.toMap(Genre::getId, Function.identity()));
        Long genreId = null;
        displayMapOnScreen(genreMap);
        do {
            Long answer = Long.parseLong(waitAnswer("Please, choose genre number", Pattern.compile("^\\d+")));
            genreId = genreMap.containsKey(answer) ? answer : null;
        } while (genreId == null);

        return genreMap.get(genreId);
    }

    private List<Author> chooseAuthors() {
        Map<Long, Author> authorMap = libraryService.getAllAuthors().stream().collect(Collectors.toMap(Author::getId, Function.identity()));
        List<Author> authorList = null;
        displayMapOnScreen(authorMap);
        do {
            String answer = waitAnswer("Please, choose authors, separate their numbers by comma", Pattern.compile("^\\d+(,?\\s*\\d*)*"));
            authorList = checkAnswersInMap(answer, authorMap);
        } while (authorList == null || authorList.isEmpty());

        return authorList;
    }

    private <V> List<V> checkAnswersInMap(String string, Map<Long, V> map) {
        return Arrays.stream(string.split(",\\s*"))
                .map(Long::valueOf)
                .map(map::get)
                .collect(Collectors.toList());
    }

    private <K, V> void displayMapOnScreen(Map<K, V> map) {
        map.entrySet().forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
    }

    private String waitAnswer(String invite) {
        System.out.println(invite);
        return scanner.nextLine();
    }

    private String waitAnswer(String invite, Pattern pattern) {
        String result;
        do {
            System.out.println(invite);
            String line = scanner.nextLine();
            Matcher matcher = pattern.matcher(line);
            result = matcher.find() ? matcher.group(0) : null;
        } while (result == null);

        return result;
    }
}
