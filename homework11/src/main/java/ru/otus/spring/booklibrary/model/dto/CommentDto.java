package ru.otus.spring.booklibrary.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentDto {
    private final String text;
    private final BookDto bookDto;
    private final LocalDateTime create;

    @Override
    public String toString() {
        return text;
    }
}
