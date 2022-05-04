package cit.com.repository.impl

import cit.com.model.User
import cit.com.repository.UserRepository

class UserRepositoryImpl: UserRepository {

    private val users = mapOf<String, User>(
        "admin:admin" to User(1, "John"),
        "user:user" to User(1, "Paul")
    )

    override fun getUser(username: String, password: String): User? {
        return users["$username:$password"]
    }
}
