insert into authors(id, firstName, lastName) values(1, 'Naomi', 'Novik');
insert into authors(id, firstName, lastName) values(2, 'Rebecca', 'Roanhorse');
insert into authors(id, firstName, lastName) values(3, 'Martha', 'Wells');

insert into genres(id, name) values (1, 'sci-fi');
insert into genres(id, name) values (2, 'horror');
insert into genres(id, name) values (3, 'comedy');

insert into books(id, title) values(1, 'Spinning Silver');
insert into bookAuthors(bookId, authorId) values(1, 1);
insert into bookGenres(bookId, genreId) values(1, 3);

insert into books(id, title) values(2, 'Trail of Lightning');
insert into bookAuthors(bookId, authorId) values(2, 2);
insert into bookGenres(bookId, genreId) values(2, 2);
insert into bookGenres(bookId, genreId) values(2, 1);

insert into books(id, title) values(3, 'Artificial Condition');
insert into bookAuthors(bookId, authorId) values(3, 1);
insert into bookAuthors(bookId, authorId) values(3, 2);
insert into bookGenres(bookId, genreId) values(3, 3);


