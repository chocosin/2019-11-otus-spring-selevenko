package ru.otus.spring.hw5.dao

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import ru.otus.spring.hw5.domain.Genre

@DataJpaTest
@Import(JpaGenreDao::class)
internal class JpaGenreDaoTest {
    @Autowired
    lateinit var dao: JpaGenreDao
    @Autowired
    lateinit var em: TestEntityManager

    @Test
    internal fun `inserts genre`() {
        val g = randomGenre()
        dao.insert(g)

        assertThat(find(g.id)).isEqualTo(g)
    }

    @Test
    internal fun `updates genre`() {
        val g = randomGenre()
        em.persist(g)

        val changed = g.copy(name = "changed")
        dao.update(changed)

        assertThat(find(g.id)).isEqualTo(changed)
    }

    private fun find(id: Int) = em.find(Genre::class.java, id)
}