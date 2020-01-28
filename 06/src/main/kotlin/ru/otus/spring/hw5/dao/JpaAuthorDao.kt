package ru.otus.spring.hw5.dao

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.otus.spring.hw5.domain.Author
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
@Transactional
class JpaAuthorDao(
        @PersistenceContext
        val em: EntityManager
) : AuthorDao {
    override fun getById(id: Int): Author? {
        return em.find(Author::class.java, id)
    }

    override fun getByIds(ids: List<Int>): List<Author> {
        return ids.map { getById(it) }.filterNotNull()
    }

    override fun insert(author: Author): Int {
        em.persist(author)
        return author.id
    }

    override fun update(author: Author) {
        em.merge(author)
    }

    override fun list(): List<Author> {
        return em.createQuery("select a from Author a", Author::class.java)
                .resultList
    }

    override fun delete(id: Int) {
        getById(id)?.also {
            em.remove(it)
        }
    }
}
