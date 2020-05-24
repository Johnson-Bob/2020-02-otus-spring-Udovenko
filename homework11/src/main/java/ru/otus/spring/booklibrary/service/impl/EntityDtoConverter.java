package ru.otus.spring.booklibrary.service.impl;

import ru.otus.spring.booklibrary.model.dto.AuthorDto;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.dto.CommentDto;
import ru.otus.spring.booklibrary.model.entity.Author;
import ru.otus.spring.booklibrary.model.entity.Book;
import ru.otus.spring.booklibrary.model.entity.Comment;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

class EntityDtoConverter {
    public static BookDto toBookDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .bookTitle(book.getTitle())
                .genre(book.getGenre())
                .authors(toSetAuthorDto(book.getAuthors()))
                .build();
    }

    public static List<BookDto> toListBookDto(Collection<Book> books) {
        return StreamSupport.stream(books.spliterator(), false)
                .map(EntityDtoConverter::toBookDto)
                .collect(toList());
    }

    public static AuthorDto toAuthorDto(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }

    public static Set<AuthorDto> toSetAuthorDto(Collection<Author> authors) {
        return StreamSupport.stream(authors.spliterator(), false)
                .map(EntityDtoConverter::toAuthorDto)
                .collect(Collectors.toSet());
    }

    public static Author toAuthor(AuthorDto dto) {
        return new Author(dto.getId(), dto.getFirstName(), dto.getLastName());
    }

    public static Set<Author> toAuthorSet(Collection<AuthorDto> dtoSet) {
        return dtoSet.stream()
                .map(EntityDtoConverter::toAuthor)
                .collect(Collectors.toSet());
    }

    public static CommentDto toCommentDto(Comment comment, Book book) {
        return CommentDto.builder()
                .bookDto(toBookDto(book))
                .text(comment.getText())
                .create(comment.getCreate())
                .build();
    }

    public static List<CommentDto> toListCommentDto(Book book) {
        return book.getComments().stream().map(c -> toCommentDto(c, book)).collect(toList());
    }
}
