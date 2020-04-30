package ru.otus.spring.booklibrary.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Document(collection = "authors")
public class Author {
    @MongoId(targetType = FieldType.OBJECT_ID)
    private String id;
    private String firstName;
    private String lastName;

    @PersistenceConstructor
    public Author(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
