package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import model.Post

@Composable
fun HomeScreen() {
    var list by remember { mutableStateOf(listOf<Post>()) }
    LaunchedEffect(Unit) {
        list = getPosts()
    }
    MaterialTheme {
        Dashboard(list)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(userData: List<Post>) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { innerPadding ->
            AnimatedVisibility(userData.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize(),
                    content = {
                        items(userData) {
                            PostItem(it)
                        }
                    },
                    contentPadding = innerPadding
                )
            }
        }
    )
}

suspend fun getPosts(): List<Post> {
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

@Composable
fun PostItem(post: Post) {
    Column {
        Text(
            text = post.username
        )
        Text(
            text = post.caption
        )
    }
}