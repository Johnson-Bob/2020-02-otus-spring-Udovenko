package ru.otus.spring.booklibrary.presentation;

import ru.otus.spring.booklibrary.model.dto.AuthorDto;
import ru.otus.spring.booklibrary.model.dto.BookDto;
import ru.otus.spring.booklibrary.model.dto.CommentDto;
import ru.otus.spring.booklibrary.model.web.AuthorModel;
import ru.otus.spring.booklibrary.model.web.BookModel;
import ru.otus.spring.booklibrary.model.web.CommentModel;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class ModelDtoConverter {
    public static AuthorModel toAuthorModel(AuthorDto dto) {

        if (dto == null) {
            return null;
        }
        AuthorModel authorModel = new AuthorModel();
        authorModel.setId(dto.getId());
        authorModel.setFirstName(dto.getFirstName());
        authorModel.setLastName(dto.getLastName());
        return authorModel;
    }

    public static Set<AuthorModel> toAuthorModelSet(Collection<AuthorDto> dtoSet) {
        return dtoSet.stream()
                .map(ModelDtoConverter::toAuthorModel)
                .collect(Collectors.toSet());
    }

    public static AuthorDto toAuthorDto(AuthorModel model) {

        if (model == null) {
            return null;
        }

        return AuthorDto.builder()
                .id(model.getId())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .build();
    }

    public static CommentModel toCommentModel(CommentDto dto) {
        if (dto == null) {
            return null;
        }

        CommentModel commentModel = new CommentModel();
        commentModel.setText(dto.getText());
        commentModel.setCreate(dto.getCreate());
        commentModel.setBookId(dto.getBookDto().getId());

        return commentModel;
    }

    public static List<CommentModel> toCommentModelList(Collection<CommentDto> dtoCollection) {
        return dtoCollection.stream()
                .map(ModelDtoConverter::toCommentModel)
                .collect(Collectors.toList());
    }

    public static CommentDto toCommentDto(CommentModel model) {
        if (model == null) {
            return null;
        }

        return CommentDto.builder()
                .text(model.getText())
                .bookDto(BookDto.builder().id(model.getBookId()).build())
                .create(model.getCreate())
                .build();
    }

    public static BookDto toBookDto(BookModel model) {

        if (model == null) {
            return null;
        }
        Set<AuthorDto> authorDtoSet = model.getAuthors().stream()
                .map(ModelDtoConverter::toAuthorDto)
                .collect(Collectors.toSet());

        return BookDto.builder()
                .id(model.getId())
                .bookTitle(model.getBookTitle())
                .genre(model.getGenre())
                .authors(authorDtoSet)
                .build();
    }

    public static BookModel toBookModel(BookDto dto) {

        if (dto == null) {
            return null;
        }

        BookModel bookModel = new BookModel();
        bookModel.setId(dto.getId());
        bookModel.setBookTitle(dto.getBookTitle());
        bookModel.setGenre(dto.getGenre());
        bookModel.setAuthors(toAuthorModelSet(dto.getAuthors()));
        return bookModel;
    }

    public static List<BookModel> toBookModelList(Collection<BookDto> dtoList) {
        return dtoList.stream()
                .map(ModelDtoConverter::toBookModel)
                .collect(Collectors.toList());
    }
}
