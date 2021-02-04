CREATE TABLE IF NOT EXISTS users (
       id SERIAL PRIMARY KEY,
       email VARCHAR NOT NULL,
       passwordhash VARCHAR NOT NULL,
       passwordsalt VARCHAR NOT NULL
);
