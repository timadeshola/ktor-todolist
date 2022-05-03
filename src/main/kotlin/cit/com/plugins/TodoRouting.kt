package cit.com.plugins

import cit.com.authentication.JwtConfig
import cit.com.model.JwtUser
import cit.com.model.LoginRequest
import cit.com.model.TodoRequest
import cit.com.repository.MysqlTodoRepository
import cit.com.repository.TodoRepository
import cit.com.repository.UserRepository
import cit.com.repository.UserRepositoryImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.todoRouting() {

    val repos: TodoRepository = MysqlTodoRepository()
    val userRepo: UserRepository = UserRepositoryImpl()
    val jwtConfig = JwtConfig()
    routing {

        post("login") {
            val loginRequest = call.receive<LoginRequest>()
            val user = userRepo.getUser(loginRequest.username, loginRequest.password)
            if (user == null) {
                call.respondText("Invalid username or password", status = HttpStatusCode.Unauthorized)
                return@post
            }
            val token = jwtConfig.generateToken(JwtUser(user.id, user.username))
            call.respond(token)
        }

        authenticate {

            get("me") {
                val user = call.authentication.principal as JwtUser
                call.respond(user)
            }

            get("/todos") {
                val todos = repos.getAllTodos()
                if (todos.isEmpty()) {
                    call.respondText("No record found", status = HttpStatusCode.NotFound)
                    return@get
                }
                call.respond(todos)
            }

            get("/todos/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respondText("Missing integer ID parameter", status = HttpStatusCode.BadRequest)
                    return@get
                }
                val todo = repos.getTodo(id)
                if (todo == null) {
                    call.respondText("No record found with id: $id", status = HttpStatusCode.NotFound)
                    return@get
                }
                call.respond(todo)
            }

            post("/todos") {
                val request = call.receive<TodoRequest>()
                val todo = repos.addTodo(request)
                call.respond(todo!!)
            }

            put("todos/{id}") {
                val request = call.receive<TodoRequest>()
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respondText("Missing integer ID parameter", status = HttpStatusCode.BadRequest)
                    return@put
                }
                val todo = repos.updateTodo(request, id)
                if (todo) {
                    call.respond(HttpStatusCode.OK, todo)
                } else {
                    call.respondText("Record not found", status = HttpStatusCode.NotFound)
                }
            }

            delete("todos/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respondText("Missing integer ID parameter", status = HttpStatusCode.BadRequest)
                    return@delete
                }
                val todo = repos.removeTodo(id)
                if (todo) {
                    call.respond(HttpStatusCode.OK, todo)
                } else {
                    call.respondText("Record not found", status = HttpStatusCode.NotFound)
                }
            }
        }

    }
}
