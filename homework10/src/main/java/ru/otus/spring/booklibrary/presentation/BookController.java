package ru.otus.spring.booklibrary.presentation;

import static ru.otus.spring.booklibrary.presentation.ModelDtoConverter.*;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.web.BookModel;
import ru.otus.spring.booklibrary.service.BookService;

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
  public List<BookModel> findBookByName(@RequestParam String title) {
    final List<BookDto> booksByName = bookService.findBookByName(title);
    return toBookModelList(booksByName);
  }

  @PutMapping("/books")
  public BookModel updateBook(@Valid @RequestBody BookModel model) {
    final BookDto dto = toBookDto(model);
    final BookDto afterUpdate = bookService.updateBook(dto);

    return toBookModel(afterUpdate);
  }

}
