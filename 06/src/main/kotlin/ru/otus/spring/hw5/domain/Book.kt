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
                cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH]
        )
        @JoinTable(
                name = "book_authors",
                joinColumns = [JoinColumn(name = "book_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "author_id", referencedColumnName = "id")]
        )
        var genres: MutableSet<Genre> = mutableSetOf(),

        @ManyToMany(
                fetch = FetchType.EAGER,
                cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH]
        )
        @JoinTable(
                name = "book_genres",
                joinColumns = [JoinColumn(name = "book_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "genre_id", referencedColumnName = "id")]
        )
        var authors: MutableSet<Author> = mutableSetOf()
) {
    constructor() : this(0L, "", mutableSetOf(), mutableSetOf())
}
