package ru.otus.spring.hw5.dao

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import ru.otus.spring.hw5.domain.Author
import ru.otus.spring.hw5.domain.Book
import ru.otus.spring.hw5.domain.Genre

@DataJpaTest
@Import(JpaBookDao::class)
internal class JpaBookDaoTest {
    @Autowired
    lateinit var dao: JpaBookDao
    @Autowired
    lateinit var em: TestEntityManager

    @Test
    internal fun `inserts book`() {
        val book = randomBook()
        dao.insert(book)
        val dbBook = em.find(Book::class.java, book.id)
        assertThat(dbBook).isEqualTo(book)
    }

    @Test
    internal fun `inserts book with detached genre`() {
        val g = randomGenre()
        em.persist(g)

        val book = randomBook(
                genres = mutableSetOf(g.copy())
        )
        dao.insert(book)

        val dbBook = em.find(Book::class.java, book.id)
        assertThat(dbBook).isEqualTo(book)
    }

    @Test
    internal fun `change book genres`() {
        val (g1, g2, g3) = List(3) { randomGenre() }
        val book = randomBook(
                genres = mutableSetOf(g1, g2)
        )
        em.persist(book)
        em.persist(g3)

        val changed = book.copy(
                genres = mutableSetOf(g1, g3)
        )
        dao.update(changed)

        val dbBook = em.find(Book::class.java, book.id)
        assertThat(dbBook).isEqualTo(changed)

        // g2 is not removed
        assertThat(em.find(Genre::class.java, g2.id)).isEqualTo(g2)
    }

    @Test
    internal fun `delete book`() {
        val g = randomGenre()
        val author = randomAuthor()
        val book = randomBook(
                genres = mutableSetOf(g),
                authors = mutableSetOf(author)
        )
        em.persist(book)

        dao.delete(book.id)

        val dbBook = em.find(Book::class.java, book.id)
        assertThat(dbBook).isNull()

        // authors and genres are not removed
        assertThat(em.find(Genre::class.java, g.id)).isEqualTo(g)
        assertThat(em.find(Author::class.java, author.id)).isEqualTo(author)
    }

    @Test
    internal fun `list all books`() {
        val books = List(10) { randomBook().also { em.persist(it) } }
        assertThat(dao.list())
                .containsExactlyInAnyOrderElementsOf(books)
                .allMatch { it.authors.isNotEmpty() }
                .allMatch { it.genres.isNotEmpty() }
    }
}