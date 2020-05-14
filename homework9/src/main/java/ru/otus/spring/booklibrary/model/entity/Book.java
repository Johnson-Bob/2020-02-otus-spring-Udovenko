package ru.otus.spring.booklibrary.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "books")
public class Book {
    @MongoId(targetType = FieldType.OBJECT_ID)
    private String id;

    private String title;

    private String genre;

    @Field(targetType = FieldType.ARRAY)
    private Set<Author> authors;

    @Field(targetType = FieldType.ARRAY)
    private Set<Comment> comments;

    @Version
    private Long version;

    @PersistenceConstructor
    public Book(String id, String title, String genre, Set<Author> authors) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.authors = authors;
    }

    public void addComment(Comment comment) {
        if (comments == null) {
            comments = new LinkedHashSet<>();
        }
        comments.add(comment);
    }

    public void deleteComment(Comment comment) {
        comments.remove(comment);
    }
}
