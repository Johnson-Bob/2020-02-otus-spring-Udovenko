package ru.otus.spring.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.otus.spring.entity.Answer;

@Getter
@Setter
@RequiredArgsConstructor
public class AnswerDto {
    private final long userId;
    private final long questionId;
    private final char userAnswer;
    private Answer correctAnswer;
    private boolean isUserAnswerCorrect;
}
