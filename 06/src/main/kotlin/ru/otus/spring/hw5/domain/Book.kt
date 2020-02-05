package ru.otus.spring.hw5.domain

import javax.persistence.*

@Entity
@Table(name = "books")
data class Book(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,
        var title: String,

        @ManyToMany(
                fetch = FetchType.EAGER,
                cascade = [CascadeType.MERGE, CascadeType.REFRESH]
        )
        @JoinTable(
                name = "book_authors",
                joinColumns = [JoinColumn(name = "book_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "author_id", referencedColumnName = "id")]
        )
        var genres: MutableSet<Genre> = mutableSetOf(),

        @ManyToMany(
                fetch = FetchType.EAGER,
                cascade = [CascadeType.MERGE, CascadeType.REFRESH]
        )
        @JoinTable(
                name = "book_genres",
                joinColumns = [JoinColumn(name = "book_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "genre_id", referencedColumnName = "id")]
        )
        var authors: MutableSet<Author> = mutableSetOf(),

        @OneToMany(
                fetch = FetchType.LAZY,
                orphanRemoval = true,
                cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE],
                mappedBy = "book"
        )
        var comments: List<BookComment> = mutableListOf()
) {
    constructor() : this(0L, "", mutableSetOf(), mutableSetOf())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        if (id != other.id) return false
        if (title != other.title) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        return result
    }

    override fun toString(): String {
        return "Book(id=$id, title='$title', genres=$genres, authors=$authors"
    }
}
