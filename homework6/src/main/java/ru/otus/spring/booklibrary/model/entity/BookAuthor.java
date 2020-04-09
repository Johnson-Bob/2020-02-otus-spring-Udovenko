package ru.otus.spring.booklibrary.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BookAuthor {
    private final long bookId;
    private final long authorId;
}
