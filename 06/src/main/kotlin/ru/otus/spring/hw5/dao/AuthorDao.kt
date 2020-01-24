package ru.otus.spring.hw5.dao

import ru.otus.spring.hw5.domain.Author

interface AuthorDao {
    fun getById(id: Int): Author?
    fun getByIds(ids: List<Int>): List<Author>
    fun insert(author: Author): Int
    fun update(author: Author)
    fun list(): List<Author>
    fun delete(id: Int)
}
