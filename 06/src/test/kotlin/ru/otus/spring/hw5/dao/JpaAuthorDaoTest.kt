package ru.otus.spring.hw5.dao

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import ru.otus.spring.hw5.domain.Author

@DataJpaTest
@Import(JpaAuthorDao::class)
internal class JpaAuthorDaoTest {
    @Autowired
    lateinit var dao: JpaAuthorDao
    @Autowired
    lateinit var em: TestEntityManager

    @Test
    internal fun `can insert and get by id`() {
        val author = randomAuthor()
        dao.insert(author)
        assertThat(author.id).isGreaterThan(0)

        val dbAuthor = em.find(Author::class.java, author.id)
        assertThat(dbAuthor).isEqualTo(author)
    }

    @Test
    internal fun `gets author by id`() {
        val author = randomAuthor()
        em.persist(author)

        assertThat(dao.getById(author.id)).isEqualTo(author)
    }

    @Test
    internal fun `updates author`() {
        val author = randomAuthor()
        em.persist(author)

        val changed = author.copy(firstName = "changed", lastName = "changed")
        dao.update(changed)

        assertThat(em.find(Author::class.java, author.id)).isEqualTo(author)
    }

    @Test
    internal fun `list authors`() {
        val authors = List(10) { randomAuthor() }
        authors.forEach { em.persist(it) }

        assertThat(dao.list()).containsExactlyInAnyOrderElementsOf(authors)
    }

    @Test
    internal fun `deletes author`() {
        val author = randomAuthor()
        em.persist(author)

        dao.delete(author.id)

        assertThat(em.find(Author::class.java, author.id)).isNull()
    }
}