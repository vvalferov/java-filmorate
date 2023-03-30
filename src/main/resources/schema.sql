drop table if exists FRIEND_STATUS, ALL_GENRES, GENRE, LIKES, FILMS, MPA, USERS;

create table if not exists USERS
(
    user_id  INTEGER auto_increment,
    email    varchar not null,
    login    varchar not null,
    name     varchar,
    birthday DATE    not null,

    constraint users_ primary key (user_id),
    constraint mail_login UNIQUE (email, login)
);

create table if not exists FRIEND_STATUS
(
    user_id   INTEGER not null,
    friend_id INTEGER not null,
    constraint primary_key_fs primary key (user_id, friend_id),
    constraint user_id_delete foreign key (user_id) references USERS ON DELETE CASCADE,
    constraint friend_id_delete foreign key (friend_id) references USERS ON DELETE CASCADE,
    constraint id_differ check (user_id <> friend_id)
);

create table if not exists MPA
(
    mpa_name varchar not null,
    mpa_id   INTEGER auto_increment,
    constraint primary_key_mpa primary key (mpa_id)
);

create table if not exists FILMS
(
    film_id      INTEGER auto_increment,
    name         varchar not null,
    description  varchar(200),
    release_date DATE    not null,
    duration     INTEGER not null,
    mpa_id       INTEGER,
    constraint primary_key_films primary key (film_id),
    constraint mpa_id_ref foreign key (mpa_id) references MPA (mpa_id)
);

create table if not exists LIKES
(
    film_id INTEGER not null,
    user_id INTEGER not null,
    constraint primary_key_likes primary key (film_id, user_id),
    constraint user_id_delete_likes foreign key (user_id) references USERS ON DELETE CASCADE,
    constraint film_id_ref foreign key (film_id) references FILMS
);

create table if not exists GENRE
(
    genre_name varchar not null,
    genre_id   INTEGER auto_increment,
    constraint primary_key_genre primary key (genre_id)
);


create table if not exists ALL_GENRES
(
    film_id  INTEGER not null,
    genre_id INTEGER not null,
    constraint all_genres_id
        primary key (film_id, genre_id),
    constraint all_genres_ref1
        foreign key (genre_id) references GENRE,
    constraint all_genres_ref2
        foreign key (film_id) references FILMS
);
