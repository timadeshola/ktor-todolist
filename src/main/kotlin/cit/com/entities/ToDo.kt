package cit.com.entities

import cit.com.database.TodoEntity

@kotlinx.serialization.Serializable
data class ToDo(
    val id: Int,
    var title: String,
    var done: Boolean
)

public fun toTodo(data: TodoEntity): ToDo =
    ToDo(
        id = data.id,
        title = data.title,
        done = data.done
    )
