CREATE TABLE rating(
    id INT PRIMARY KEY,
    rating SMALLINT NOT NULL,
    user_id INT NOT NULL,
    movie_id INT NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movie(id),
    CONSTRAINT uc_rating UNIQUE (user_id, movie_id)
);