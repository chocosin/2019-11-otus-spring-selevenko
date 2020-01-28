package ru.otus.spring.hw5.dao

import ru.otus.spring.hw5.domain.Author
import ru.otus.spring.hw5.domain.Genre
import kotlin.random.Random.Default.nextInt

fun GenreDao.insertRandom() =
        randomGenre()
                .also { genre ->
                    insert(genre).let { id -> genre.copy(id = id) }
                }

fun randomGenre(): Genre {
    val rnd = nextInt()
    return Genre(
            id = 0,
            name = "genre-$rnd"
    )
}

fun AuthorDao.insertRandom() =
        randomAuthor().also { author ->
            insert(author).let { id -> author.copy(id = id) }
        }

fun randomAuthor(): Author {
    val rnd = nextInt()
    return Author(
            id = 0,
            firstName = "firstName-$rnd",
            lastName = "lastName-$rnd"
    )
}