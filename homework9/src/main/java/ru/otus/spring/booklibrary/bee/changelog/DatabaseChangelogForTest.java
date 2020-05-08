package ru.otus.spring.booklibrary.bee.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.springframework.context.annotation.Profile;
import ru.otus.spring.booklibrary.model.entity.Comment;
import ru.otus.spring.booklibrary.model.entity.Genre;

import java.time.LocalDateTime;
import java.util.List;


@ChangeLog
@Profile("test")
public class DatabaseChangelogForTest {

    @ChangeSet(order = "001", id = "addGenres", author = "vudovenko")
    public void insertGenres(DB db) {
        DBCollection collection = db.getCollection("genres");
        DBObject sciFi = new BasicDBObject().append("genreName", "Science fiction");
        DBObject fan = new BasicDBObject().append("genreName", "Fantasy");
        DBObject det = new BasicDBObject().append("genreName", "Detective");
        DBObject adv = new BasicDBObject().append("genreName", "Adventure");
        collection.insert(sciFi, fan, det, adv);
    }

    @ChangeSet(order = "002", id = "addAuthors", author = "vudovenko")
    public void insertAuthors(DB db) {
        DBCollection collection = db.getCollection("authors");
        DBObject ivanov = new BasicDBObject().append("firstName", "Ivan").append("lastName", "Ivanov");
        DBObject petrov = new BasicDBObject().append("firstName", "Petr").append("lastName", "Petrov");
        DBObject sidorov = new BasicDBObject().append("firstName", "Sidor").append("lastName", "Sidorov");
        collection.insert(ivanov, petrov, sidorov);
    }

    @ChangeSet(order = "003", id = "addBook", author = "vudovenko")
    public void insertBooks(DB db) {
        DBCollection authors = db.getCollection("authors");

        BasicDBList fBookAuthors = new BasicDBList();
        DBObject ivanov = authors.find(new BasicDBObject("lastName", "Ivanov")).next();
        DBObject petrov = authors.find(new BasicDBObject("lastName", "Petrov")).next();
        fBookAuthors.addAll(List.of(ivanov, petrov));

        BasicDBList sBookAuthors = new BasicDBList();
        DBObject sidorov = authors.find(new BasicDBObject("lastName", "Sidorov")).next();
        sBookAuthors.add(sidorov);

        BasicDBList fBookComments = new BasicDBList();
        DBObject comment1 = new BasicDBObject("text", "comment1").append("create", LocalDateTime.now());
        DBObject comment2 = new BasicDBObject("text", "comment2").append("create", LocalDateTime.now());
        fBookComments.addAll(List.of(comment1, comment2));

        DBObject firstBook = new BasicDBObject("title", "My first book")
                .append("genre", "Science fiction")
                .append("authors", fBookAuthors)
                .append("comments", fBookComments);
        DBObject secondBook = new BasicDBObject("title", "My second book")
                .append("genre", "Fantasy")
                .append("authors", sBookAuthors);
        DBCollection books = db.getCollection("books");
        books.insert(firstBook, secondBook);
    }
}
