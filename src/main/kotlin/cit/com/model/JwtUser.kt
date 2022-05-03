package cit.com.model

import io.ktor.server.auth.*

@kotlinx.serialization.Serializable
data class JwtUser(val id: Int, val username: String) : Principal
