package cit.com.repository

import cit.com.model.TodoRequest
import cit.com.entities.ToDo

interface TodoRepository {

    fun getAllTodos(): List<ToDo>

    fun getTodo(id: Int): ToDo?

    fun addTodo(request: TodoRequest): ToDo?

    fun updateTodo(request: TodoRequest, id: Int): Boolean

    fun removeTodo(id: Int): Boolean

}
