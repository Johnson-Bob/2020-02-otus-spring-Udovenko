package ru.otus.spring.booklibrary.service;

import ru.otus.spring.booklibrary.model.dto.AuthorDto;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.dto.CommentDto;
import ru.otus.spring.booklibrary.model.dto.GenreDto;
import ru.otus.spring.booklibrary.model.entity.Author;
import ru.otus.spring.booklibrary.model.entity.Book;
import ru.otus.spring.booklibrary.model.entity.Comment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DtoConverter {
    public static BookDto convertToBookDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .bookName(book.getName())
                .genreDto(GenreDto.builder().id(book.getGenre().getId()).genreName(book.getGenre().getGenreName()).build())
                .authors(convertToSetAuthorDto(book.getAuthors()))
                .build();
    }

    public static List<BookDto> convertToListBookDto(List<Book> books) {
        List<BookDto> result = new ArrayList<>(books.size());
        books.forEach(book -> result.add(convertToBookDto(book)));

        return result;
    }

    public static AuthorDto convertToAuthorDto(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }

    public static Set<AuthorDto> convertToSetAuthorDto(Collection<Author> authors) {
        return authors.stream()
                .map(DtoConverter::convertToAuthorDto)
                .collect(Collectors.toSet());
    }

    public static Comment convertToComment(CommentDto commentDto) {
        BookDto bookDto = commentDto.getBookDto();
        Book book = new Book();
        book.setId(bookDto.getId());
        return new Comment(commentDto.getId(), book, commentDto.getText());
    }

    public static CommentDto convertToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .bookDto(BookDto.builder().id(comment.getBook().getId()).bookName(comment.getBook().getName()).build())
                .text(comment.getText())
                .build();
    }
}
