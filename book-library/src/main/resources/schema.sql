--Table authors
CREATE TABLE IF NOT EXISTS author (
    id INT8 AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id));

--Table genres
CREATE TABLE IF NOT EXISTS genre (
    id INT8 AUTO_INCREMENT,
    genre_name VARCHAR(250) NOT NULL,
    PRIMARY KEY (id));

--Table books
CREATE TABLE IF NOT EXISTS book (
    id INT8 AUTO_INCREMENT,
    genre_id INT8 NOT NULL,
    name VARCHAR(250) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (genre_id) REFERENCES genre (id));

--Table book_author
CREATE TABLE IF NOT EXISTS book_author (
    book_id INT8 REFERENCES book(id) ON DELETE CASCADE,
    author_id INT8 REFERENCES author(id),
    PRIMARY KEY (book_id, author_id));