CREATE TABLE rating(
    id SERIAL PRIMARY KEY,
    rating SMALLINT NOT NULL,
    user_id BIGINT NOT NULL,
    movie_id BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movie(id),
    CONSTRAINT uc_rating UNIQUE (user_id, movie_id)
);