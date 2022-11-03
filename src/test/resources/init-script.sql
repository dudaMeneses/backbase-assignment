CREATE TABLE movie(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) UNIQUE NOT NULL,
    box_value DECIMAL(12,2)
);

CREATE TABLE oscar_nomination(
    id SERIAL PRIMARY KEY,
    year SMALLINT NOT NULL,
    nominee VARCHAR(1000) NOT NULL,
    category VARCHAR(200) NOT NULL,
    won BOOLEAN NOT NULL
);

CREATE TABLE rating(
    id SERIAL PRIMARY KEY,
    rating SMALLINT NOT NULL,
    user_id BIGINT NOT NULL,
    movie_id BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movie(id),
    CONSTRAINT uc_rating UNIQUE (user_id, movie_id)
);