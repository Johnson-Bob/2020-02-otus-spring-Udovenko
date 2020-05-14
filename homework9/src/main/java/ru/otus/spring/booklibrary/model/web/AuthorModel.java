package ru.otus.spring.booklibrary.model.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorModel {
    private String id;
    private String firstName;
    private String lastName;
}
