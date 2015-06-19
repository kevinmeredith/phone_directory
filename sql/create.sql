CREATE TYPE sex AS ENUM ('male', 'female');

CREATE TABLE person (
    id bigserial primary key,
    name varchar(100) NOT NULL,
    age integer NOT NULL,
    gender sex NOT NULL
);