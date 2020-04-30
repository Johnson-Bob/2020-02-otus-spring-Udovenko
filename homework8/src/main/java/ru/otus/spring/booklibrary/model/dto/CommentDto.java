package ru.otus.spring.booklibrary.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentDto {
    private String text;
    private BookDto bookDto;
    private LocalDateTime create;

    @Override
    public String toString() {
        return text;
    }
}
