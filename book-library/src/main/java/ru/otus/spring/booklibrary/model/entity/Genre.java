package ru.otus.spring.booklibrary.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    private long id;
    private String genreName;

    @Override
    public String toString() {
        return genreName;
    }
}
