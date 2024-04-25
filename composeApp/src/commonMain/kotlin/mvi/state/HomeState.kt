package mvi.state

import model.Post
import model.Story

data class HomeState(
    val posts: List<Post> = arrayListOf(),
    val stories: List<Story> = arrayListOf(),
    val isLoading: Boolean = false,
    val isPostLiked: Boolean = false
)