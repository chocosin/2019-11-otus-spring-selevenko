package ru.otus.spring.hw4

import org.springframework.stereotype.Component

@Component
class User(
        var fullName: String? = null
) {
    fun isLoggedIn() = fullName != null

    fun logIn(name: String) =
            NAME_REGEX.matches(name).also {
                if (it) fullName = name
            }
}

private val NAME_REGEX = "^\\w+ \\w+$".toRegex()