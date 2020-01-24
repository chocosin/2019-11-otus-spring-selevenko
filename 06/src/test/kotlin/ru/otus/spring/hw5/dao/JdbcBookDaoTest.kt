package ru.otus.spring.hw5.dao

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.Import
import ru.otus.spring.hw5.domain.Book
import kotlin.random.Random

@JdbcTest
@Import(JdbcBookDao::class, JdbcGenreDao::class, JdbcAuthorDao::class)
internal class JdbcBookDaoTest {
    @Autowired
    private lateinit var bookDao: BookDao
    @Autowired
    private lateinit var authorDao: AuthorDao
    @Autowired
    private lateinit var genreDao: GenreDao

    @Test
    internal fun `insert book and get by id`() {
        val book = insertRandom()
        assertThat(bookDao.getById(book.id)).isEqualTo(book)
    }

    @Test
    internal fun `updates title`() {
        val books = List(3) { insertRandom() }
        val changed = books[1].copy(title = "new title")
        bookDao.update(changed)
        assertThat(bookDao.list()).isEqualTo(listOf(books[0], changed, books[2]))
    }

    @Test
    internal fun `inserts several and lists all`() {
        val books = List(10) { insertRandom() }
        assertThat(bookDao.list()).isEqualTo(books)
    }

    @Test
    internal fun `deletes book`() {
        val book = insertRandom()
        bookDao.delete(book.id)
        assertThat(bookDao.getById(book.id)).isNull()
    }

    private fun insertRandom(
            authors: Int = (1..4).random(),
            genres: Int = (1..4).random()
    ) = Random.nextInt().let { rnd ->
        val book = Book(
                id = -1,
                title = "title-$rnd",
                authors = List(authors) { authorDao.insertRandom() },
                genres = List(genres) { genreDao.insertRandom() }
        )
        bookDao.insert(book).let { book.copy(id = it) }
    }

}