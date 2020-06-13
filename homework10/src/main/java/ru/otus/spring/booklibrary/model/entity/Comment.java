package ru.otus.spring.booklibrary.model.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"create"})
public class Comment {

  private String text;

  @Field(name = "create", targetType = FieldType.DATE_TIME)
  private LocalDateTime create;

  @PersistenceConstructor
  public Comment(String text, LocalDateTime create) {
    this.text = text;
    this.create = create;
  }
}
