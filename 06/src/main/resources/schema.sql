create table books
(
  id    bigint primary key auto_increment,
  title varchar(30)
);

create table authors
(
  id        bigint primary key auto_increment,
  firstName varchar(30),
  lastName  varchar(30)
);

create table genres
(
  id   int primary key auto_increment,
  name varchar(30)
);

create table bookAuthors
(
  bookId   bigint,
  authorId bigint,
  primary key (bookId, authorId)
);

create table bookGenres
(
  bookId  bigint,
  genreId int,
  primary key (bookId, genreId)
);
