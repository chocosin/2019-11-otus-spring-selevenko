package ru.otus.spring.hw5.dao

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.Import

@JdbcTest
@Import(JdbcAuthorDao::class)
internal class JdbcAuthorDaoTest {
    @Autowired
    lateinit var authorDao: AuthorDao

    @Test
    internal fun `inserts new author and gets correct id`() {
        val author = authorDao.insertRandom()

        assertThat(authorDao.getById(author.id)).isEqualTo(author)
    }

    @Test
    internal fun `inserts 2 new authors and gets the by ids`() {
        val authors = List(3) { authorDao.insertRandom() }

        assertThat(authorDao.getByIds(authors.map { it.id }))
                .containsExactlyInAnyOrderElementsOf(authors)
    }

    @Test
    internal fun `updates author`() {
        val author = authorDao.insertRandom()
        val newAuthor = author.copy(lastName = "new lastname", firstName = "new lastname")
        authorDao.update(newAuthor)
        assertThat(authorDao.getById(author.id)).isEqualTo(newAuthor)
    }

    @Test
    internal fun `inserts several and lists all`() {
        val authors = List(20) { authorDao.insertRandom() }
        assertThat(authorDao.list()).isEqualTo(authors)
    }

    @Test
    internal fun `deletes one author`() {
        val authors = List(3) { authorDao.insertRandom() }
        authorDao.delete(authors[1].id)
        assertThat(authorDao.list()).isEqualTo(listOf(authors[0], authors[2]))
        assertThat(authorDao.getById(authors[1].id)).isNull()
    }
}