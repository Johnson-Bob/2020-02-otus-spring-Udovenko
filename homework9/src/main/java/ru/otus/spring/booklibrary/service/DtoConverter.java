package ru.otus.spring.booklibrary.service;

import ru.otus.spring.booklibrary.model.dto.AuthorDto;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.dto.CommentDto;
import ru.otus.spring.booklibrary.model.entity.Author;
import ru.otus.spring.booklibrary.model.entity.Book;
import ru.otus.spring.booklibrary.model.entity.Comment;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class DtoConverter {
    public static BookDto convertToBookDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .bookTitle(book.getTitle())
                .genre(book.getGenre())
                .authors(convertToSetAuthorDto(book.getAuthors()))
                .build();
    }

    public static List<BookDto> convertToListBookDto(Iterable<Book> books) {
        return StreamSupport.stream(books.spliterator(), false)
                .map(DtoConverter::convertToBookDto)
                .collect(toList());
    }

    public static AuthorDto convertToAuthorDto(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }

    public static Set<AuthorDto> convertToSetAuthorDto(Iterable<Author> authors) {
        return StreamSupport.stream(authors.spliterator(), false)
                .map(DtoConverter::convertToAuthorDto)
                .collect(Collectors.toSet());
    }

    public static CommentDto convertToCommentDto(Comment comment, Book book) {
        return CommentDto.builder()
                .bookDto(convertToBookDto(book))
                .text(comment.getText())
                .create(comment.getCreate())
                .build();
    }

    public static List<CommentDto> convertToListCommentDto(Book book) {
        return book.getComments().stream().map(c -> convertToCommentDto(c, book)).collect(toList());
    }

    public static <T> Map<String, T> convertListToMap(List<T> list) {
        return IntStream.range(0, list.size())
                .boxed()
                .collect(Collectors.toMap(i -> Integer.toString(i + 1), list::get));
    }
}
