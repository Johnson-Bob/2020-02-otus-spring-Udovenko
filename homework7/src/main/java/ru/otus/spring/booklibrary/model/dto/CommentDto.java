package ru.otus.spring.booklibrary.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentDto {
    private Long id;
    private BookDto bookDto;
    private String text;

    @Override
    public String toString() {
        return text;
    }
}
