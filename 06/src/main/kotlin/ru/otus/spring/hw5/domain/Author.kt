package ru.otus.spring.hw5.domain

import javax.persistence.*

@Entity
@Table(name = "authors")
data class Author(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int,
        var firstName: String,
        var lastName: String
) {
    constructor() : this(0, "", "")

    @Transient
    val fullName = "$firstName $lastName"
}
