package cit.com.model

@kotlinx.serialization.Serializable
data class JwtResponse(
    val token: String,
    val expiredIn: Long,
    val username: String,
)
