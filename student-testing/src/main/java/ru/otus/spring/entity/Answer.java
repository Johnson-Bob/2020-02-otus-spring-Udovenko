package ru.otus.spring.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode(exclude = "id")
public class Answer {
    private long id; //use for database
    private char letter;
    private String answerText;
    private boolean isCorrect;

    @Override
    public String toString() {
        return letter + ") " + answerText;
    }
}
