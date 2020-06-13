package ru.otus.spring.booklibrary.model.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "genres")
public class Genre {
  private String genreName;
}
