package ru.otus.spring.hw5.dao

import ru.otus.spring.hw5.domain.Author
import ru.otus.spring.hw5.domain.Genre
import kotlin.random.Random.Default.nextInt

fun GenreDao.insertRandom() =
        nextInt().let { rnd ->
            val genre = Genre(
                    id = -1,
                    name = "genre-$rnd"
            )
            insert(genre).let { id -> genre.copy(id = id) }
        }

fun AuthorDao.insertRandom() =
        nextInt().let { rnd ->
            val author = Author(
                    id = -1,
                    firstName = "firstName-$rnd",
                    lastName = "lastName-$rnd"
            )
            insert(author).let { id -> author.copy(id = id) }
        }
