create table movie
(
    movie_id int,
    movie_name varchar,
    movie_desc varchar,
    release_date datetime,
    duration int,
    cover_photo_url varchar2,
    trailer_url varchar2
);

INSERT INTO movie (movie_id, movie_name, duration ) values
(1, 'INCEPTION', 200),
(2,'BAHUBALI', 120);