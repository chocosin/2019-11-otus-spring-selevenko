package ru.otus.spring.hw5.dao

import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import ru.otus.spring.hw5.domain.Book

private object Books {
    const val table = "books"
    const val id = "id"
    const val title = "title"

    const val columns = "$id, $title"
    val rowMapper = RowMapper { rs, _ ->
        Book(rs.getLong(id), rs.getString(title))
    }
}

private object BookAuthors {
    const val table = "bookAuthors"
    const val bookId = "bookId"
    const val authorId = "authorId"
}

private object BookGenres {
    const val table = "bookGenres"
    const val bookId = "bookId"
    const val genreId = "genreId"
}

@Repository
class JdbcBookDao(
        private val jdbc: NamedParameterJdbcOperations,
        private val genreDao: GenreDao,
        private val authorDao: AuthorDao
) : BookDao {
    override fun getById(bookId: Long): Book? {
        val book = with(Books) {
            try {
                jdbc.queryForObject(
                        "select $columns from $table where $id=:id",
                        mapOf(
                                "id" to bookId
                        ),
                        rowMapper
                )
            } catch (e: IncorrectResultSizeDataAccessException) {
                return null
            }
        }

        if (book == null)
            return null

        return fillBookAuthorsAndGenres(book)
    }


    override fun insert(book: Book): Long {
        val keyHolder = GeneratedKeyHolder()

        with(Books) {
            jdbc.update(
                    "insert into $table ($title) values(:title)",
                    MapSqlParameterSource(mapOf(
                            "title" to book.title
                    )),
                    keyHolder
            )
        }
        val id = keyHolder.key!!.toLong()
        book.copy(id = id).also {
            updateAuthors(it)
            updateGenres(it)
        }
        return id
    }

    override fun update(book: Book) {
        with(Books) {
            jdbc.update(
                    "update $table set $title=:title where $id=:id",
                    mapOf(
                            "title" to book.title,
                            "id" to book.id
                    )
            )
        }

        updateGenres(book)
        updateAuthors(book)
    }

    override fun list(): List<Book> =
            with(Books) {
                jdbc.query(
                        "select $columns from $table",
                        rowMapper
                )
            }.map { fillBookAuthorsAndGenres(it) }

    override fun delete(id: Long) {
        with(Books) {
            jdbc.update(
                    "delete from $table where ${this.id}=:id",
                    mapOf(
                            "id" to id
                    )
            )
        }
        with(BookAuthors) {
            jdbc.update(
                    "delete from $table where $bookId=:id",
                    mapOf("id" to id)
            )
        }
        with(BookGenres) {
            jdbc.update(
                    "delete from $table where $bookId=:id",
                    mapOf("id" to id)
            )
        }
    }

    private fun fillBookAuthorsAndGenres(book: Book): Book {
        val bookId = book.id
        val genreIds = with(BookGenres) {
            jdbc.queryForList(
                    "select ($genreId) from $table where ${this.bookId}=:bookId",
                    mapOf(
                            "bookId" to bookId
                    ),
                    Int::class.java
            )
        }
        val genres = genreDao.getByIds(genreIds)

        val authorIds = with(BookAuthors) {
            jdbc.queryForList(
                    "select ($authorId) from $table where ${this.bookId}=:bookId",
                    mapOf(
                            "bookId" to bookId
                    ),
                    Int::class.java
            )
        }
        val authors = authorDao.getByIds(authorIds)

        return book.copy(
                authors = authors,
                genres = genres
        )

    }

    private fun updateGenres(book: Book) {
        with(BookGenres) {
            jdbc.update(
                    "delete from $table where $bookId=:bookId",
                    mapOf(
                            "bookId" to book.id
                    )
            )
            book.genres.forEach { genre ->
                jdbc.update(
                        "insert into $table($bookId, $genreId) values(:bookId, :genreId)",
                        mapOf(
                                "bookId" to book.id,
                                "genreId" to genre.id
                        )
                )
            }
        }
    }

    private fun updateAuthors(book: Book) {
        with(BookAuthors) {
            jdbc.update(
                    "delete from $table where $bookId=:bookId",
                    mapOf(
                            "bookId" to book.id
                    )
            )
            book.authors.forEach { author ->
                jdbc.update(
                        "insert into $table($bookId, $authorId) values(:bookId, :authorId)",
                        mapOf(
                                "bookId" to book.id,
                                "authorId" to author.id
                        )
                )
            }
        }
    }
}
