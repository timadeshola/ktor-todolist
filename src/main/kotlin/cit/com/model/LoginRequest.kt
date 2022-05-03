package cit.com.model

@kotlinx.serialization.Serializable
data class LoginRequest(val username: String, val password: String)
