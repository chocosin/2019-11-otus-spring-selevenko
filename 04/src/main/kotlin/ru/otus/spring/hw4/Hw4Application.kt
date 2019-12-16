package ru.otus.spring.hw4

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource

@SpringBootApplication
class Hw4Application {
    @Bean
    fun gameFileResource(props: ApplicationProperties): Resource = ClassPathResource(props.questionFile)

    @Bean
    @ConfigurationProperties("app")
    fun applicationProperties() = ApplicationProperties()
}

fun main(args: Array<String>) {
    runApplication<Hw4Application>(*args)
}
