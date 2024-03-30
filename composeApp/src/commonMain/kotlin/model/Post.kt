package model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val username: String = "",
    val caption: String = "",
    val isVerified: Boolean = false,
    val likes: Int = 0,
    val imgPath: List<String> = arrayListOf(),
    val isLiked: Boolean = false
)