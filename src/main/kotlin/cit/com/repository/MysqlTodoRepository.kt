package cit.com.repository

import cit.com.database.DatabaseManager
import cit.com.entities.ToDo
import cit.com.entities.toTodo
import cit.com.model.TodoRequest

class MysqlTodoRepository : TodoRepository {

    private val database = DatabaseManager()

    override fun getAllTodos(): List<ToDo> {
        return database.getAll().toList().map { toTodo(it) }
    }

    override fun getTodo(id: Int): ToDo? {
        return database.getTodo(id)?.let { toTodo(it) }
    }

    override fun addTodo(request: TodoRequest): ToDo? {
        return database.addTodo(request)
    }

    override fun updateTodo(request: TodoRequest, id: Int): Boolean {
        return database.updateTodo(request, id)
    }

    override fun removeTodo(id: Int): Boolean {
        return database.removeTodo(id)
    }
}
