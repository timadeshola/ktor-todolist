package cit.com.model

@kotlinx.serialization.Serializable
data class TodoRequest(
    val title: String,
    val done: Boolean
)
