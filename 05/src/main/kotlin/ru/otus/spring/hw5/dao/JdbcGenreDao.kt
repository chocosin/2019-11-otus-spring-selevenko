package ru.otus.spring.hw5.dao

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import ru.otus.spring.hw5.domain.Genre

private const val TABLE = "genres"
private const val ID = "id"
private const val NAME = "name"
private const val COLUMNS = "$ID, $NAME"

@Repository
class JdbcGenreDao(
        private val jdbc: NamedParameterJdbcOperations
) : GenreDao {
    override fun getById(id: Int) = getByIds(listOf(id)).firstOrNull()

    override fun getByIds(ids: List<Int>): List<Genre> {
        return jdbc.query(
                "select $COLUMNS from $TABLE where $ID in (:ids)",
                mapOf(
                        "ids" to ids
                ),
                genreRowMapper
        )
    }

    override fun list(): List<Genre> = jdbc.query(
            "select $COLUMNS from $TABLE",
            genreRowMapper
    )

    override fun insert(genre: Genre): Int {
        val keyHolder = GeneratedKeyHolder()
        jdbc.update(
                "insert into $TABLE($NAME) values (:name)",
                MapSqlParameterSource(
                        mapOf(
                                "name" to genre.name
                        )
                ),
                keyHolder
        )
        return keyHolder.key!!.toInt()
    }

    override fun update(genre: Genre) {
        jdbc.update(
                "update $TABLE set $NAME=:name WHERE $ID=:id",
                mapOf(
                        "id" to genre.id,
                        "name" to genre.name
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

private val genreRowMapper = RowMapper { rs, _ ->
    Genre(
            id = rs.getInt(ID),
            name = rs.getString(NAME)
    )
}
