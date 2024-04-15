package model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class Story(
    val profilePic: String = "",
    val username: String = ""
)