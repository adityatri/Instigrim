import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import instigrim.composeapp.generated.resources.Res
import instigrim.composeapp.generated.resources.compose_multiplatform

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            var list by remember { mutableStateOf(listOf<Post>()) }
            LaunchedEffect(Unit) {
                list = getPosts()
            }
            LazyColumn {
                items(list) {
                    PostItem(it)
                }
            }
        }
    }
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