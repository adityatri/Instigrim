package model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class Comment(
    val profilePic: String = "",
    val userComment: String = "",
    val username: String = "",
    val isVerified: Boolean = false,
    val likes: Int = 0,
    val isLikedByCreator: Boolean = false
)