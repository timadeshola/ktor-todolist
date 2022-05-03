package cit.com.database

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object TodoTable : Table<TodoEntity>("todos") {
    val id = int("id").primaryKey().bindTo { it.id }
    val title = varchar("title").bindTo { it.title }
    val done = boolean("done").bindTo { it.done }
}

interface TodoEntity : Entity<TodoEntity> {
    companion object : Entity.Factory<TodoEntity>()

    val id: Int
    val title: String
    val done: Boolean

}
