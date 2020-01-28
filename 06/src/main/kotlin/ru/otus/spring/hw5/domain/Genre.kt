package ru.otus.spring.hw5.domain

import javax.persistence.*

@Entity
@Table(name = "genres")
data class Genre(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,
        val name: String
) {
    constructor() : this(0, "")
}
