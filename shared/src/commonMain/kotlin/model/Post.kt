package model

data class Post(
    val username: String = "",
    val isVerified: Boolean = false,
    val likes: Int = 0,
    val comments: List<String>? = null,
    val description: String = "",
    val profilePic: String = "",
    val postedPhoto: String = "",
    val location: String? = null,
    val publishedDate: String = "",
)
