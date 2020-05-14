package ru.otus.spring.booklibrary.model.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentModel {
    private String text;
    private LocalDateTime create;
}
