package ru.otus.spring.hw5.domain

import javax.persistence.*

@Entity
@Table(name = "authors")
data class Author(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int,
        @get:Column(name = "firstName")
        var firstName: String,
        @get:Column(name = "lastName")
        var lastName: String
) {
    constructor() : this(0, "", "")

    @Transient
    val fullName = "$firstName $lastName"
}
