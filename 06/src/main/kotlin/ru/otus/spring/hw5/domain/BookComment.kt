package ru.otus.spring.hw5.domain

import javax.persistence.*

@Entity
@Table(name = "book_comments")
data class BookComment(
        @Id
        @Column(name = "comment_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,

        var comment: String,

        @ManyToOne(
                optional = false,
                fetch = FetchType.LAZY
        )
        @JoinColumn(name = "book_id")
        var book: Book
) {
    private constructor() : this(0L, "", Book())

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is BookComment) return false
        return id == other.id && comment == other.comment
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + comment.hashCode()
        return result
    }

    override fun toString(): String {
        return "BookComment(id=$id, comment='$comment', book=${book.id})"
    }


}
