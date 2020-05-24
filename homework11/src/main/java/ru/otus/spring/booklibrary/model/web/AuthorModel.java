package ru.otus.spring.booklibrary.model.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class AuthorModel {
    private String id;

    @NotBlank(message = "Author first name required")
    private String firstName;

    @NotBlank(message = "Author last name required")
    private String lastName;
}
