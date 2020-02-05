package ru.otus.spring.hw5.dao

import ru.otus.spring.hw5.domain.Author
import ru.otus.spring.hw5.domain.Book
import ru.otus.spring.hw5.domain.Genre
import kotlin.random.Random.Default.nextInt

fun randomGenre(): Genre {
    val rnd = nextInt()
    return Genre(
            id = 0,
            name = "genre-$rnd"
    )
}

fun randomAuthor(): Author {
    val rnd = nextInt()
    return Author(
            id = 0,
            firstName = "firstName-$rnd",
            lastName = "lastName-$rnd"
    )
}

fun randomBook(
        authors: MutableSet<Author> = List((1..4).random()) { randomAuthor() }.toMutableSet(),
        genres: MutableSet<Genre> = List((1..4).random()) { randomGenre() }.toMutableSet()
): Book {
    val rnd = nextInt()
    return Book(
            id = 0,
            title = "title-$rnd",
            authors = authors,
            genres = genres
    )
}
