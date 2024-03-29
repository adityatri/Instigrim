package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import billabongFontFamily
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
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
        topBar = {
            CustomTopAppBar(scrollBehavior = scrollBehavior)
        },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(scrollBehavior: TopAppBarScrollBehavior) {
    TopAppBar(
        title = {
            Text(
                "Instigrim",
                fontFamily = billabongFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 32.sp
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White,
            scrolledContainerColor = Color.White
        ),
        actions = {
            IconButton(onClick = { }) {
                Box(contentAlignment = Alignment.TopEnd) {
                    Icon(Icons.Filled.FavoriteBorder, "notification")
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.Red)
                            .size(8.dp),
                    )
                }
            }
            IconButton(onClick = { }) {
                Icon(Icons.Filled.ChatBubbleOutline, "messages")
            }
        },
        scrollBehavior = scrollBehavior
    )
}


@Composable
fun PostItem(post: Post) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Username(post)
        if (post.imgPath.size > 1) {
            ImageSlider(post)
        } else {
            val painterResource: Resource<Painter> = asyncPainterResource(
                post.imgPath[0],
                filterQuality = FilterQuality.High,
            )
            KamelImage(
                resource = painterResource,
                contentDescription = "",
                contentScale = ContentScale.Inside,
                modifier = Modifier.fillMaxSize(),
                onLoading = { CircularProgressIndicator(it) },
                onFailure = { exception: Throwable ->
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = exception.message.toString(),
                            actionLabel = "Hide",
                        )
                    }
                }
            )
        }
        Description(post)
    }
}

@Composable
fun Username(data: Post) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                fontWeight = FontWeight.Bold,
                text = data.username
            )
            if (data.isVerified) {
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(start = 4.dp),
                    tint = Color.Blue,
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = "verified"
                )
            }
        }
        Icon(
            modifier = Modifier
                .size(24.dp),
            imageVector = Icons.Default.MoreVert,
            contentDescription = "more"
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(data: Post) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val images = arrayListOf<String>()
    data.imgPath.map {
        images.add(it)
    }
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        data.imgPath.size
    }
    HorizontalPager(
        pageSpacing = 0.dp,
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.BottomCenter) {
                val painterResource: Resource<Painter> = asyncPainterResource(
                    images[page],
                    filterQuality = FilterQuality.High,
                )
                KamelImage(
                    resource = painterResource,
                    contentDescription = "",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier.fillMaxSize(),
                    onLoading = { CircularProgressIndicator(it) },
                    onFailure = { exception: Throwable ->
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = exception.message.toString(),
                                actionLabel = "Hide",
                            )
                        }
                    },
                )
            }
        }
    }
}

@Composable
fun Description(data: Post) {
    Column(modifier = Modifier.padding(bottom = 8.dp, start = 16.dp, end = 16.dp)) {
        Text(
            fontWeight = FontWeight.Bold,
            text = "${data.likes} likes"
        )
        Row {
            Text(
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 8.dp),
                text = data.username
            )
            Text(data.caption)
        }
        Text(
            text = "10 minutes ago",
            color = Color.DarkGray,
            fontSize = 14.sp
        )
    }
}