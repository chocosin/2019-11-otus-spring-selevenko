package ru.otus.spring.hw5.dao

import ru.otus.spring.hw5.domain.Book

interface BookDao {
    fun getById(id: Long): Book?
    fun insert(book: Book): Long
    fun update(book: Book)
    fun list(): List<Book>
    fun delete(id: Long)
}
