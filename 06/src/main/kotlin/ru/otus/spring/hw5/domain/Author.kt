package ru.otus.spring.hw5.domain

data class Author(
        val id: Int,
        val firstName: String,
        val lastName: String
) {
    val fullName = "$firstName $lastName"
}
