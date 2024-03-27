package model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val username: String,
    val caption: String,
    val isVerified: Boolean,
    val likes: String
)