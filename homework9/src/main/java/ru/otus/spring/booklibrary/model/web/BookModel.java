package ru.otus.spring.booklibrary.model.web;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookModel {
  private String id;

  @NotBlank(message = "Title required")
  private String bookTitle;

  @NotBlank(message = "Genre required")
  private String genre;

  @Size(min = 1, message = "The book must have at least one author")
  private Set<AuthorModel> authors;
}
