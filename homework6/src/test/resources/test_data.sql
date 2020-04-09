--Insert genres
INSERT INTO genre (genre_name)
VALUES ('Science fiction'), ('Fantasy'), ('Detective'), ('Adventure');

--Insert authors
INSERT INTO author (first_name, last_name)
VALUES ('Ivan', 'Ivanov'), ('Petr', 'Petrov'), ('Sidor', 'Sidorov'), ('Vasyl', 'Vasylev');

--Insert book
INSERT INTO book (genre_id, name)
VALUES (1, 'My first book'), (2, 'My second book');

--Insert book_author
INSERT INTO book_author(book_id, author_id)
VALUES (1, 1), (1, 2), (2, 3);

--Insert comments
INSERT INTO comment (book_id, text)
VALUES (1, 'comment1'), (1, 'comment2');