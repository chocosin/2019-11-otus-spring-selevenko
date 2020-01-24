package ru.otus.spring.hw5.domain

data class Book(
        val id: Long,
        val title: String,
        val genres: List<Genre> = emptyList(),
        val authors: List<Author> = emptyList()
)
