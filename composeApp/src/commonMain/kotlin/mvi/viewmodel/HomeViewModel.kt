package mvi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.launch
import model.Post
import model.Story
import mvi.action.HomeAction
import mvi.state.HomeState

class HomeViewModel : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    init {
        viewModelScope.launch {
            state = HomeState(
                posts = getPosts(),
                stories = getStories()
            )
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.ToggleLike -> toggleLike()
        }
    }

    private fun toggleLike() {

    }

    private suspend fun getPosts(): List<Post> {
        val firebaseFirestore = Firebase.firestore
        try {
            val postResponse =
                firebaseFirestore.collection("posts").get()
            return postResponse.documents.map {
                it.data()
            }
        } catch (e: Throwable) {
            throw e
        }
    }

    private suspend fun getStories(): List<Story> {
        val firebaseFirestore = Firebase.firestore
        try {
            val postResponse =
                firebaseFirestore.collection("stories").get()
            return postResponse.documents.map {
                it.data()
            }
        } catch (e: Throwable) {
            throw e
        }
    }
}