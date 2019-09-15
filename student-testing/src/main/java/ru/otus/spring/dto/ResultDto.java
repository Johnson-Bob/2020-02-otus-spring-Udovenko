package ru.otus.spring.dto;

import lombok.Builder;
import lombok.Getter;
import ru.otus.spring.entity.User;

@Getter
@Builder
public class ResultDto {
    private User user;
    private String result;
    private boolean isTestPass;
}
