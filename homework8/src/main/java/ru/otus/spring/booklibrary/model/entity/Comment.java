package ru.otus.spring.booklibrary.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
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
