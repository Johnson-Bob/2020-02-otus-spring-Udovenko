package ru.otus.spring.booklibrary.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.web.BookModel;
import ru.otus.spring.booklibrary.service.BookService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.spring.booklibrary.presentation.ModelDtoConverter.toBookDto;
import static ru.otus.spring.booklibrary.presentation.ModelDtoConverter.toBookModel;
import static ru.otus.spring.booklibrary.presentation.ModelDtoConverter.toBookModelList;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public BookModel addBookToLibrary(@Valid @RequestBody BookModel model) {
        final BookDto savedBook = bookService.addBookToLibrary(toBookDto(model));
        return toBookModel(savedBook);
    }

    @DeleteMapping("/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBookFromLibrary(@PathVariable String id) {
        bookService.deleteBookFromLibrary(BookDto.builder().id(id).build());
    }

    @GetMapping("/books/find")
    public List<BookModel> findBookByName(@RequestParam String name) {
        final List<BookDto> booksByName = bookService.findBookByName(name);
        return toBookModelList(booksByName);
    }

    @PutMapping("/books")
    public BookModel updateBook(@Valid @RequestBody BookModel model) {
        final BookDto dto = toBookDto(model);
        final BookDto afterUpdate = bookService.updateBook(dto);

        return toBookModel(afterUpdate);
    }

}
