package ru.otus.spring.hw5.dao

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import ru.otus.spring.hw5.domain.Book
import ru.otus.spring.hw5.domain.BookComment

@DataJpaTest
@Import(JpaBookCommentDao::class, JpaBookDao::class)
internal class JpaBookCommentDaoTest {
    @Autowired
    lateinit var dao: JpaBookCommentDao
    @Autowired
    lateinit var em: TestEntityManager

    @Test
    fun `adds new book comment`() {
        val book = persistRandomBook()

        val text = "new comment"
        val id = dao.addComment(book.id, text)

        val dbComment = em.find(BookComment::class.java, id)

        val expected = BookComment(id = id, comment = text, book = book)
        assertThat(dbComment).isEqualTo(expected)

        val dbBook = em.find(Book::class.java, book.id)
        assertThat(dbBook.comments).isEqualTo(listOf(expected))
    }

    @Test
    fun `adds 2 new book comment and gets them by book`() {
        val book = persistRandomBook()

        val id1 = dao.addComment(book.id, "comment1")
        val id2 = dao.addComment(book.id, "comment2")

        val comments = dao.getBookComments(book.id)

        assertThat(comments).isEqualTo(listOf(
                BookComment(id = id1, comment = "comment1", book = book),
                BookComment(id = id2, comment = "comment2", book = book)
        ))
    }

    @Test
    fun `adds 2 new book comments, deletes first one`() {
        val book = persistRandomBook()

        val id1 = dao.addComment(book.id, "comment1")
        val id2 = dao.addComment(book.id, "comment2")

        dao.removeComment(id1)

        val dbBook = em.find(Book::class.java, book.id)
        assertThat(dbBook.comments).isEqualTo(listOf(
                BookComment(id2, "comment2", book)
        ))
    }

    private fun persistRandomBook(): Book {
        val book = randomBook()
        book.authors.forEach { em.persist(it) }
        book.genres.forEach { em.persist(it) }
        em.persist(book)
        return book
    }
}
