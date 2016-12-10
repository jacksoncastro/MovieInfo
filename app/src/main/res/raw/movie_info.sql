PRAGMA foreign_keys = ON;

CREATE TABLE movie (
    id serial PRIMARY KEY,
    title TEXT NOT NULL,
    overview TEXT NOT NULL,
    poster_path TEXT NOT NULL,
    vote_average DOUBLE,
    date_view DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);