package ru.otus.spring.hw5.dao

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.otus.spring.hw5.domain.Book
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
@Transactional
class JpaBookDao(
        @PersistenceContext
        private val em: EntityManager
) : BookDao {
    override fun getById(id: Long): Book? = em.find(Book::class.java, id)

    override fun insert(book: Book): Long {
        em.merge(book)
        return book.id
    }

    override fun update(book: Book) {
        em.merge(book)
    }

    override fun list(): List<Book> {
        return em.createQuery("select b from Book b", Book::class.java)
                .resultList
    }

    override fun delete(id: Long) {
        getById(id)?.also {
            em.remove(it)
        }
    }
}