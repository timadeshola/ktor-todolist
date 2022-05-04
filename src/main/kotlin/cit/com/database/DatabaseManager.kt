package cit.com.database

import cit.com.entities.ToDo
import cit.com.model.TodoRequest
import org.ktorm.database.Database
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.dsl.update
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import org.ktorm.logging.ConsoleLogger
import org.ktorm.logging.LogLevel

val ktormDatabase = Database.connect(
    url = "jdbc:mysql://localhost:3306/todo",
    driver = "com.mysql.cj.jdbc.Driver",
    user = "root",
    password = "developer",
    logger = ConsoleLogger(threshold = LogLevel.WARN)
)

class DatabaseManager {

    fun getAll(): List<TodoEntity> {
        return ktormDatabase.sequenceOf(TodoTable).toList();
    }

    fun getTodo(id: Int): TodoEntity? {
        return ktormDatabase.sequenceOf(TodoTable).firstOrNull { it.id eq id }
    }

    fun addTodo(request: TodoRequest): ToDo {
        val insert = ktormDatabase.insert(TodoTable) {
            set(TodoTable.title, request.title)
            set(TodoTable.done, request.done)
        }
        return ToDo(insert, request.title, request.done)
    }

    fun updateTodo(request: TodoRequest, id: Int): Boolean {
        val update = ktormDatabase.update(TodoTable) {
            set(TodoTable.title, request.title)
            set(TodoTable.done, request.done)
            where { it.id eq id }
        }
        return update > 0
    }

    fun removeTodo(id: Int): Boolean {
        val delete = ktormDatabase.delete(TodoTable) {
            it.id eq id
        }
        return delete > 0
    }

}
