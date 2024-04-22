create table users
(
    id serial not null primary key,
    email VARCHAR,
    first_name  VARCHAR,
    last_name   VARCHAR,
    phone VARCHAR,
    role VARCHAR,
    password VARCHAR,
    image_id INTEGER
);

create table ads
(
    id serial not null primary key,
    title   VARCHAR,
    description VARCHAR,
    price INTEGER,
    image VARCHAR,
    data bytea,
    user_id INTEGER
);

create table comments
(
    id serial not null primary key,
    ad_id INTEGER,
    author_id INTEGER,
    created_at TIMESTAMP,
    text VARCHAR
);