package ru.otus.spring.hw5.dao

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.otus.spring.hw5.domain.Genre
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
@Transactional
class JpaGenreDao(
        @PersistenceContext
        private val em: EntityManager
) : GenreDao {
    override fun getById(id: Int): Genre? = em.find(Genre::class.java, id)

    override fun getByIds(ids: List<Int>): List<Genre> =
            ids.mapNotNull { getById(it) }

    override fun insert(genre: Genre): Int =
            genre.also { em.persist(it) }.id

    override fun update(genre: Genre) {
        em.merge(genre)
    }

    override fun list(): List<Genre> {
        return em.createQuery("select g from Genre g", Genre::class.java)
                .resultList
    }

    override fun delete(id: Int) {
        getById(id)?.also { em.remove(it) }
    }
}