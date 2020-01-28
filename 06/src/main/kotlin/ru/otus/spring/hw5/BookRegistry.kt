package ru.otus.spring.hw5

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import ru.otus.spring.hw5.dao.AuthorDao
import ru.otus.spring.hw5.dao.BookDao
import ru.otus.spring.hw5.dao.GenreDao
import ru.otus.spring.hw5.domain.Author
import ru.otus.spring.hw5.domain.Book
import ru.otus.spring.hw5.domain.Genre

@ShellComponent
class BookRegistry(
        private val bookDao: BookDao,
        @Qualifier("jpaAuthorDao") private val authorDao: AuthorDao,
        private val genreDao: GenreDao
) {

    @ShellMethod(value = "list books", key = ["bl", "bookList"])
    fun listBooks() = bookDao.list().joinToString("\n")

    @ShellMethod(value = "insert new book", key = ["bi", "insertBook"])
    fun insertBook(
            @ShellOption("-t") title: String,
            @ShellOption("-g") genreId: String,
            @ShellOption("-a") authorId: String
    ): String {
        val genre = genreDao.getById(genreId.toInt()) ?: return "genre not found"
        val author = authorDao.getById(authorId.toInt()) ?: return "author not found"
        val book = Book(
                id = 0,
                title = title,
                genres = listOf(genre),
                authors = listOf(author)
        )
        val id = bookDao.insert(book)
        return book.copy(id = id).toString()
    }

    @ShellMethod(value = "change book title", key = ["bct", "changeBookTitle"])
    fun changeBookTitle(bookId: String, title: String): String {
        val book = bookDao.getById(bookId.toLong()) ?: return "book not found"
        bookDao.update(book.copy(title = title))
        return bookDao.getById(book.id)!!.toString()
    }

    @ShellMethod(value = "delete book", key = ["bd", "deleteBook"])
    fun deleteBook(bookId: String): String {
        val book = bookDao.getById(bookId.toLong()) ?: return "book not found"
        bookDao.delete(book.id)
        return "ok"
    }

    @ShellMethod(value = "add book author", key = ["baa", "addBookAuthor"])
    fun addBookAuthor(
            bookId: String,
            authorId: String
    ): String {
        val book = bookDao.getById(bookId.toLong()) ?: return "book not found"
        val author = authorDao.getById(authorId.toInt()) ?: return "author not found"
        val authors = book.authors.associateBy { it.id } + (author.id to author)
        bookDao.update(book.copy(authors = authors.values.toList()))

        return bookDao.getById(book.id).toString()
    }

    @ShellMethod(value = "add book genre", key = ["bga", "add book genre"])
    fun addBookGenre(
            bookId: String,
            genreId: String
    ): String {
        val book = bookDao.getById(bookId.toLong()) ?: return "book not found"
        val genre = genreDao.getById(genreId.toInt()) ?: return "genre not found"
        val genres = book.genres.associateBy { it.id } + (genre.id to genre)
        bookDao.update(book.copy(genres = genres.values.toList()))

        return bookDao.getById(book.id).toString()
    }

    @ShellMethod(value = "list authors", key = ["al", "listAuthors"])
    fun listAuthors() = authorDao.list().joinToString("\n")

    @ShellMethod(value = "insert author", key = ["ai", "insertAuthor"])
    fun insertAuthor(
            first: String,
            last: String
    ): String {
        val author = Author(
                id = 0,
                lastName = last,
                firstName = first
        )
        return authorDao.insert(author).let { author.copy(id = it) }.toString()
    }

    @ShellMethod(value = "insert genre", key = ["gi", "insertGenre"])
    fun insertGenre(
            name: String
    ): String {
        val genre = Genre(
                id = 0,
                name = name
        )
        return genreDao.insert(genre).let { genre.copy(id = it) }.toString()
    }

    @ShellMethod(value = "list genres", key = ["gl", "listGenres"])
    fun listGenres() = genreDao.list().joinToString("\n")

}