package cit.com.repository.impl

import cit.com.model.TodoRequest
import cit.com.entities.ToDo
import cit.com.repository.TodoRepository

class TodoRepositoryImpl : TodoRepository {

    private val todos = mutableListOf(
        ToDo(1, "Publish video 1", true),
        ToDo(2, "Record video #1", false),
        ToDo(3, "Upload video #1", false)
    )

    // in memory management
    override fun getAllTodos(): List<ToDo> {
        return todos
    }

    override fun getTodo(id: Int): ToDo? {
        return todos.firstOrNull { it.id == id }
    }

    override fun addTodo(request: TodoRequest): ToDo? {
        val todo = ToDo(todos.size + 1, request.title, request.done)
        todos.add(todo)
        return todo
    }

    override fun updateTodo(request: TodoRequest, id: Int): Boolean {
        val todo = todos.firstOrNull { it.id == id } ?: return false
        todo.title = request.title
        todo.done = request.done
        return true;
    }

    override fun removeTodo(id: Int): Boolean {
        return todos.removeIf { it.id == id }
    }
}
