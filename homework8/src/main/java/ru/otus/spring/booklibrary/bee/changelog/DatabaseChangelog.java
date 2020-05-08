package ru.otus.spring.booklibrary.bee.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;


@ChangeLog
public class DatabaseChangelog {

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
}
