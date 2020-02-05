insert into authors(id, first_name, last_name) values(1, 'Naomi', 'Novik');
insert into authors(id, first_name, last_name) values(2, 'Rebecca', 'Roanhorse');
insert into authors(id, first_name, last_name) values(3, 'Martha', 'Wells');

insert into genres(id, name) values (1, 'sci-fi');
insert into genres(id, name) values (2, 'horror');
insert into genres(id, name) values (3, 'comedy');

insert into books(id, title) values(1, 'Spinning Silver');
insert into book_authors(book_id, author_id) values(1, 1);
insert into book_genres(book_id, genre_id) values(1, 3);
insert into book_comments(book_id, comment_id, comment) values (1, 1, 'Spinning silver - cool book');
insert into book_comments(book_id, comment_id, comment) values (1, 2, 'Spinning silver - boring book');

insert into books(id, title) values(2, 'Trail of Lightning');
insert into book_authors(book_id, author_id) values(2, 2);
insert into book_genres(book_id, genre_id) values(2, 2);
insert into book_genres(book_id, genre_id) values(2, 1);
insert into book_comments(book_id, comment_id, comment) values (2, 3, 'Trail of Lightning - cool book');
insert into book_comments(book_id, comment_id, comment) values (2, 4, 'Trail of Lightning - boring book');

insert into books(id, title) values(3, 'Artificial Condition');
insert into book_authors(book_id, author_id) values(3, 1);
insert into book_authors(book_id, author_id) values(3, 2);
insert into book_genres(book_id, genre_id) values(3, 3);
insert into book_comments(book_id, comment_id, comment) values (3, 5, 'Artificial Condition - cool book');
insert into book_comments(book_id, comment_id, comment) values (3, 6, 'Artificial Condition - boring book');

