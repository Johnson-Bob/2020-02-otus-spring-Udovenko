package ru.otus.spring.booklibrary.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GenreDto {
    private Long id;

    private String genreName;

    @Override
    public String toString() {
        return genreName;
    }
}
