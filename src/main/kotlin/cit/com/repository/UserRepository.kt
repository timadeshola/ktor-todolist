package cit.com.repository

import cit.com.model.User

interface UserRepository {

    fun getUser(username: String, password: String): User?
}
