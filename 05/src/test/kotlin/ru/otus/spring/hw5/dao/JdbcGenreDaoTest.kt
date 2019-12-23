package ru.otus.spring.hw5.dao

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.Import

@JdbcTest
@Import(JdbcGenreDao::class)
internal class JdbcGenreDaoTest {
    @Autowired
    lateinit var genreDao: GenreDao

    @Test
    internal fun `inserts genre`() {
        val genre = genreDao.insertRandom()

        assertThat(genreDao.getById(genre.id)).isEqualTo(genre)
    }

    @Test
    internal fun `inserts and updates genre`() {
        val genre = genreDao.insertRandom()

        genreDao.update(genre.copy(name = "new name"))

        assertThat(genreDao.getById(genre.id)!!.name).isEqualTo("new name")
    }

    @Test
    internal fun `returns null if doesn't exist`() {
        assertThat(genreDao.getById(Int.MAX_VALUE)).isNull()
    }

    @Test
    internal fun `gets 2 genres by ids`() {
        val genres = List(2) { genreDao.insertRandom() }
        val actual = genreDao.getByIds(genres.map { it.id })
        assertThat(actual).containsExactlyInAnyOrderElementsOf(genres)
    }

    @Test
    internal fun `inserts several and lists`() {
        val genres = List(5) { genreDao.insertRandom() }
        assertThat(genreDao.list()).isEqualTo(genres)
    }

    @Test
    internal fun `deletes by id`() {
        val genres = List(3) { genreDao.insertRandom() }
        genreDao.delete(genres[1].id)
        assertThat(genreDao.getById(genres[1].id)).isNull()
        assertThat(genreDao.list()).isEqualTo(listOf(genres[0], genres[2]))
    }
}