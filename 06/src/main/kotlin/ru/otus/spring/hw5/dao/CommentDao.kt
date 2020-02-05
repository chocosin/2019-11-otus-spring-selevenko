package ru.otus.spring.hw5.dao

import ru.otus.spring.hw5.domain.BookComment

interface CommentDao {
    fun addComment(bookId: Long, text: String): Long
    fun removeComment(commentId: Long)
    fun getBookComments(bookId: Long): List<BookComment>
}
