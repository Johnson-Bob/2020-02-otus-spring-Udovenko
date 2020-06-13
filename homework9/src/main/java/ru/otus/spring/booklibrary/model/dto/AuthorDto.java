package ru.otus.spring.booklibrary.model.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class AuthorDto {
  private final String id;
  private final String firstName;
  private final String lastName;

  @Override
  public String toString() {
    return firstName + " " + lastName;
  }

  public String shortString() {
    return firstName.charAt(0) + ". " + lastName;
  }
}
