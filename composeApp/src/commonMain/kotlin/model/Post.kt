package model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.math.abs

@Serializable
@Immutable
data class Post(
    val id: Long = abs((0..999999999999).random()),
    val username: String = "",
    val caption: String = "",
    val isVerified: Boolean = false,
    val likes: Int = 0,
    val imgPath: List<String> = arrayListOf(),
    val isLiked: Boolean = false,
    val profilePic: String = "",
    val commentCount: Int = 0
)