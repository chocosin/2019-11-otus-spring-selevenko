package ru.otus.spring.hw5.dao

import ru.otus.spring.hw5.domain.Genre

interface GenreDao {
    fun getById(id: Int): Genre?
    fun getByIds(ids: List<Int>): List<Genre>
    fun insert(genre: Genre): Int
    fun update(genre: Genre)
    fun list(): List<Genre>
    fun delete(id: Int)
}