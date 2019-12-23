package ru.otus.spring.hw5.dao

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import ru.otus.spring.hw5.domain.Author

private const val TABLE = "authors"
private const val ID = "id"
private const val FIRST_NAME = "firstName"
private const val LAST_NAME = "lastName"
private const val COLUMNS = "$ID, $FIRST_NAME, $LAST_NAME"

@Repository
class JdbcAuthorDao(
        private val jdbc: NamedParameterJdbcOperations
) : AuthorDao {
    override fun getById(id: Int) = getByIds(listOf(id)).firstOrNull()

    override fun getByIds(ids: List<Int>): List<Author> =
            jdbc.query(
                    "select $COLUMNS from $TABLE where id in (:ids)",
                    mapOf(
                            "ids" to ids
                    ),
                    authorRowMapper
            )

    override fun list(): List<Author> = jdbc.query(
            "select $COLUMNS from $TABLE order by $ID",
            authorRowMapper
    )

    override fun insert(author: Author): Int {
        val keyHolder = GeneratedKeyHolder()
        jdbc.update(
                "insert into $TABLE($FIRST_NAME, $LAST_NAME) values (:first, :last)",
                MapSqlParameterSource(mapOf(
                        "first" to author.firstName,
                        "last" to author.lastName
                )),
                keyHolder
        )
        return keyHolder.key!!.toInt()
    }

    override fun update(author: Author) {
        jdbc.update(
                "update $TABLE set $FIRST_NAME=:first, $LAST_NAME=:last where $ID=:id",
                mapOf(
                        "id" to author.id,
                        "first" to author.firstName,
                        "last" to author.lastName
                )
        )
    }

    override fun delete(id: Int) {
        jdbc.update(
                "delete from $TABLE where $ID=:id",
                mapOf(
                        "id" to id
                )
        )
    }
}

private val authorRowMapper = RowMapper { rs, _ ->
    Author(
            id = rs.getInt(ID),
            firstName = rs.getString(FIRST_NAME),
            lastName = rs.getString(LAST_NAME)
    )
}