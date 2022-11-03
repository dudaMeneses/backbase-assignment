CREATE TABLE oscar_nomination(
    id INT PRIMARY KEY,
    year SMALLINT NOT NULL,
    nominee VARCHAR(1000) NOT NULL,
    category VARCHAR(200) NOT NULL,
    won BOOLEAN NOT NULL
)