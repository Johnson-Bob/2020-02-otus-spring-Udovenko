package ru.otus.spring.booklibrary.model.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class AuthorDto {
    private String id;
    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public String shortString() {
        return firstName.charAt(0) + ". " + lastName;
    }
}
