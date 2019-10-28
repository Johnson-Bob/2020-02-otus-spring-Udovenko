package ru.otus.spring.booklibrary.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private long id;
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
